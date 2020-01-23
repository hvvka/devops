import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ProgramStudiowComponentsPage, ProgramStudiowDeleteDialog, ProgramStudiowUpdatePage } from './program-studiow.page-object';

const expect = chai.expect;

describe('ProgramStudiow e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let programStudiowComponentsPage: ProgramStudiowComponentsPage;
  let programStudiowUpdatePage: ProgramStudiowUpdatePage;
  let programStudiowDeleteDialog: ProgramStudiowDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load ProgramStudiows', async () => {
    await navBarPage.goToEntity('program-studiow');
    programStudiowComponentsPage = new ProgramStudiowComponentsPage();
    await browser.wait(ec.visibilityOf(programStudiowComponentsPage.title), 5000);
    expect(await programStudiowComponentsPage.getTitle()).to.eq('appApp.programStudiow.home.title');
  });

  it('should load create ProgramStudiow page', async () => {
    await programStudiowComponentsPage.clickOnCreateButton();
    programStudiowUpdatePage = new ProgramStudiowUpdatePage();
    expect(await programStudiowUpdatePage.getPageTitle()).to.eq('appApp.programStudiow.home.createOrEditLabel');
    await programStudiowUpdatePage.cancel();
  });

  it('should create and save ProgramStudiows', async () => {
    const nbButtonsBeforeCreate = await programStudiowComponentsPage.countDeleteButtons();

    await programStudiowComponentsPage.clickOnCreateButton();
    await promise.all([
      programStudiowUpdatePage.profilKsztalceniaSelectLastOption(),
      programStudiowUpdatePage.formaStudiowSelectLastOption(),
      programStudiowUpdatePage.setKierunekInput('kierunek'),
      programStudiowUpdatePage.setWydzialInput('wydzial'),
      programStudiowUpdatePage.jezykProwadzeniaStudiowSelectLastOption(),
      programStudiowUpdatePage.setLiczbaSemestrowInput('5'),
      programStudiowUpdatePage.setCyklKsztalceniaInput('cyklKsztalcenia'),
      programStudiowUpdatePage.typStudiowSelectLastOption()
    ]);
    expect(await programStudiowUpdatePage.getKierunekInput()).to.eq('kierunek', 'Expected Kierunek value to be equals to kierunek');
    expect(await programStudiowUpdatePage.getWydzialInput()).to.eq('wydzial', 'Expected Wydzial value to be equals to wydzial');
    expect(await programStudiowUpdatePage.getLiczbaSemestrowInput()).to.eq('5', 'Expected liczbaSemestrow value to be equals to 5');
    expect(await programStudiowUpdatePage.getCyklKsztalceniaInput()).to.eq(
      'cyklKsztalcenia',
      'Expected CyklKsztalcenia value to be equals to cyklKsztalcenia'
    );
    await programStudiowUpdatePage.save();
    expect(await programStudiowUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await programStudiowComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last ProgramStudiow', async () => {
    const nbButtonsBeforeDelete = await programStudiowComponentsPage.countDeleteButtons();
    await programStudiowComponentsPage.clickOnLastDeleteButton();

    programStudiowDeleteDialog = new ProgramStudiowDeleteDialog();
    expect(await programStudiowDeleteDialog.getDialogTitle()).to.eq('appApp.programStudiow.delete.question');
    await programStudiowDeleteDialog.clickOnConfirmButton();

    expect(await programStudiowComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
