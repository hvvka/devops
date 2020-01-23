import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { TypStudiowComponentsPage, TypStudiowDeleteDialog, TypStudiowUpdatePage } from './typ-studiow.page-object';

const expect = chai.expect;

describe('TypStudiow e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let typStudiowComponentsPage: TypStudiowComponentsPage;
  let typStudiowUpdatePage: TypStudiowUpdatePage;
  let typStudiowDeleteDialog: TypStudiowDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load TypStudiows', async () => {
    await navBarPage.goToEntity('typ-studiow');
    typStudiowComponentsPage = new TypStudiowComponentsPage();
    await browser.wait(ec.visibilityOf(typStudiowComponentsPage.title), 5000);
    expect(await typStudiowComponentsPage.getTitle()).to.eq('appApp.typStudiow.home.title');
  });

  it('should load create TypStudiow page', async () => {
    await typStudiowComponentsPage.clickOnCreateButton();
    typStudiowUpdatePage = new TypStudiowUpdatePage();
    expect(await typStudiowUpdatePage.getPageTitle()).to.eq('appApp.typStudiow.home.createOrEditLabel');
    await typStudiowUpdatePage.cancel();
  });

  it('should create and save TypStudiows', async () => {
    const nbButtonsBeforeCreate = await typStudiowComponentsPage.countDeleteButtons();

    await typStudiowComponentsPage.clickOnCreateButton();
    await promise.all([typStudiowUpdatePage.setNazwaInput('nazwa'), typStudiowUpdatePage.setStopienStudiowInput('stopienStudiow')]);
    expect(await typStudiowUpdatePage.getNazwaInput()).to.eq('nazwa', 'Expected Nazwa value to be equals to nazwa');
    expect(await typStudiowUpdatePage.getStopienStudiowInput()).to.eq(
      'stopienStudiow',
      'Expected StopienStudiow value to be equals to stopienStudiow'
    );
    await typStudiowUpdatePage.save();
    expect(await typStudiowUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await typStudiowComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last TypStudiow', async () => {
    const nbButtonsBeforeDelete = await typStudiowComponentsPage.countDeleteButtons();
    await typStudiowComponentsPage.clickOnLastDeleteButton();

    typStudiowDeleteDialog = new TypStudiowDeleteDialog();
    expect(await typStudiowDeleteDialog.getDialogTitle()).to.eq('appApp.typStudiow.delete.question');
    await typStudiowDeleteDialog.clickOnConfirmButton();

    expect(await typStudiowComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
