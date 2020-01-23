import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { KartaPrzedmiotuComponentsPage, KartaPrzedmiotuDeleteDialog, KartaPrzedmiotuUpdatePage } from './karta-przedmiotu.page-object';

const expect = chai.expect;

describe('KartaPrzedmiotu e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let kartaPrzedmiotuComponentsPage: KartaPrzedmiotuComponentsPage;
  let kartaPrzedmiotuUpdatePage: KartaPrzedmiotuUpdatePage;
  let kartaPrzedmiotuDeleteDialog: KartaPrzedmiotuDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load KartaPrzedmiotus', async () => {
    await navBarPage.goToEntity('karta-przedmiotu');
    kartaPrzedmiotuComponentsPage = new KartaPrzedmiotuComponentsPage();
    await browser.wait(ec.visibilityOf(kartaPrzedmiotuComponentsPage.title), 5000);
    expect(await kartaPrzedmiotuComponentsPage.getTitle()).to.eq('appApp.kartaPrzedmiotu.home.title');
  });

  it('should load create KartaPrzedmiotu page', async () => {
    await kartaPrzedmiotuComponentsPage.clickOnCreateButton();
    kartaPrzedmiotuUpdatePage = new KartaPrzedmiotuUpdatePage();
    expect(await kartaPrzedmiotuUpdatePage.getPageTitle()).to.eq('appApp.kartaPrzedmiotu.home.createOrEditLabel');
    await kartaPrzedmiotuUpdatePage.cancel();
  });

  it('should create and save KartaPrzedmiotus', async () => {
    const nbButtonsBeforeCreate = await kartaPrzedmiotuComponentsPage.countDeleteButtons();

    await kartaPrzedmiotuComponentsPage.clickOnCreateButton();
    await promise.all([kartaPrzedmiotuUpdatePage.setNazwaInput('nazwa')]);
    expect(await kartaPrzedmiotuUpdatePage.getNazwaInput()).to.eq('nazwa', 'Expected Nazwa value to be equals to nazwa');
    await kartaPrzedmiotuUpdatePage.save();
    expect(await kartaPrzedmiotuUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await kartaPrzedmiotuComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last KartaPrzedmiotu', async () => {
    const nbButtonsBeforeDelete = await kartaPrzedmiotuComponentsPage.countDeleteButtons();
    await kartaPrzedmiotuComponentsPage.clickOnLastDeleteButton();

    kartaPrzedmiotuDeleteDialog = new KartaPrzedmiotuDeleteDialog();
    expect(await kartaPrzedmiotuDeleteDialog.getDialogTitle()).to.eq('appApp.kartaPrzedmiotu.delete.question');
    await kartaPrzedmiotuDeleteDialog.clickOnConfirmButton();

    expect(await kartaPrzedmiotuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
