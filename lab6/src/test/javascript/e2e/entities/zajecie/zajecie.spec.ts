import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { ZajecieComponentsPage, ZajecieDeleteDialog, ZajecieUpdatePage } from './zajecie.page-object';

const expect = chai.expect;

describe('Zajecie e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let zajecieComponentsPage: ZajecieComponentsPage;
  let zajecieUpdatePage: ZajecieUpdatePage;
  let zajecieDeleteDialog: ZajecieDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Zajecies', async () => {
    await navBarPage.goToEntity('zajecie');
    zajecieComponentsPage = new ZajecieComponentsPage();
    await browser.wait(ec.visibilityOf(zajecieComponentsPage.title), 5000);
    expect(await zajecieComponentsPage.getTitle()).to.eq('appApp.zajecie.home.title');
  });

  it('should load create Zajecie page', async () => {
    await zajecieComponentsPage.clickOnCreateButton();
    zajecieUpdatePage = new ZajecieUpdatePage();
    expect(await zajecieUpdatePage.getPageTitle()).to.eq('appApp.zajecie.home.createOrEditLabel');
    await zajecieUpdatePage.cancel();
  });

  it('should create and save Zajecies', async () => {
    const nbButtonsBeforeCreate = await zajecieComponentsPage.countDeleteButtons();

    await zajecieComponentsPage.clickOnCreateButton();
    await promise.all([
      zajecieUpdatePage.formaSelectLastOption(),
      zajecieUpdatePage.setECTSInput('5'),
      zajecieUpdatePage.setZZUInput('5'),
      zajecieUpdatePage.setCNPSInput('5'),
      zajecieUpdatePage.modulKsztalceniaSelectLastOption(),
      zajecieUpdatePage.poziomJezykaSelectLastOption(),
      zajecieUpdatePage.formaWiodacaSelectLastOption(),
      zajecieUpdatePage.grupaKursowSelectLastOption(),
      zajecieUpdatePage.przedmiotSelectLastOption()
    ]);
    expect(await zajecieUpdatePage.getECTSInput()).to.eq('5', 'Expected eCTS value to be equals to 5');
    expect(await zajecieUpdatePage.getZZUInput()).to.eq('5', 'Expected zZU value to be equals to 5');
    expect(await zajecieUpdatePage.getCNPSInput()).to.eq('5', 'Expected cNPS value to be equals to 5');
    await zajecieUpdatePage.save();
    expect(await zajecieUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await zajecieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Zajecie', async () => {
    const nbButtonsBeforeDelete = await zajecieComponentsPage.countDeleteButtons();
    await zajecieComponentsPage.clickOnLastDeleteButton();

    zajecieDeleteDialog = new ZajecieDeleteDialog();
    expect(await zajecieDeleteDialog.getDialogTitle()).to.eq('appApp.zajecie.delete.question');
    await zajecieDeleteDialog.clickOnConfirmButton();

    expect(await zajecieComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
