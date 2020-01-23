import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { PrzedmiotComponentsPage, PrzedmiotDeleteDialog, PrzedmiotUpdatePage } from './przedmiot.page-object';

const expect = chai.expect;

describe('Przedmiot e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let przedmiotComponentsPage: PrzedmiotComponentsPage;
  let przedmiotUpdatePage: PrzedmiotUpdatePage;
  let przedmiotDeleteDialog: PrzedmiotDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Przedmiots', async () => {
    await navBarPage.goToEntity('przedmiot');
    przedmiotComponentsPage = new PrzedmiotComponentsPage();
    await browser.wait(ec.visibilityOf(przedmiotComponentsPage.title), 5000);
    expect(await przedmiotComponentsPage.getTitle()).to.eq('appApp.przedmiot.home.title');
  });

  it('should load create Przedmiot page', async () => {
    await przedmiotComponentsPage.clickOnCreateButton();
    przedmiotUpdatePage = new PrzedmiotUpdatePage();
    expect(await przedmiotUpdatePage.getPageTitle()).to.eq('appApp.przedmiot.home.createOrEditLabel');
    await przedmiotUpdatePage.cancel();
  });

  it('should create and save Przedmiots', async () => {
    const nbButtonsBeforeCreate = await przedmiotComponentsPage.countDeleteButtons();

    await przedmiotComponentsPage.clickOnCreateButton();
    await promise.all([
      przedmiotUpdatePage.setNrSemestruInput('5'),
      przedmiotUpdatePage.setNazwaInput('nazwa'),
      przedmiotUpdatePage.kartaPrzedmiotuSelectLastOption(),
      przedmiotUpdatePage.opiekunPrzedmiotuSelectLastOption()
      // przedmiotUpdatePage.programStudiowSelectLastOption(),
    ]);
    expect(await przedmiotUpdatePage.getNrSemestruInput()).to.eq('5', 'Expected nrSemestru value to be equals to 5');
    expect(await przedmiotUpdatePage.getNazwaInput()).to.eq('nazwa', 'Expected Nazwa value to be equals to nazwa');
    await przedmiotUpdatePage.save();
    expect(await przedmiotUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await przedmiotComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Przedmiot', async () => {
    const nbButtonsBeforeDelete = await przedmiotComponentsPage.countDeleteButtons();
    await przedmiotComponentsPage.clickOnLastDeleteButton();

    przedmiotDeleteDialog = new PrzedmiotDeleteDialog();
    expect(await przedmiotDeleteDialog.getDialogTitle()).to.eq('appApp.przedmiot.delete.question');
    await przedmiotDeleteDialog.clickOnConfirmButton();

    expect(await przedmiotComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
