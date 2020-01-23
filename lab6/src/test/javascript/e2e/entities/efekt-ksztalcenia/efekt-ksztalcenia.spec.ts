import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { EfektKsztalceniaComponentsPage, EfektKsztalceniaDeleteDialog, EfektKsztalceniaUpdatePage } from './efekt-ksztalcenia.page-object';

const expect = chai.expect;

describe('EfektKsztalcenia e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let efektKsztalceniaComponentsPage: EfektKsztalceniaComponentsPage;
  let efektKsztalceniaUpdatePage: EfektKsztalceniaUpdatePage;
  let efektKsztalceniaDeleteDialog: EfektKsztalceniaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EfektKsztalcenias', async () => {
    await navBarPage.goToEntity('efekt-ksztalcenia');
    efektKsztalceniaComponentsPage = new EfektKsztalceniaComponentsPage();
    await browser.wait(ec.visibilityOf(efektKsztalceniaComponentsPage.title), 5000);
    expect(await efektKsztalceniaComponentsPage.getTitle()).to.eq('appApp.efektKsztalcenia.home.title');
  });

  it('should load create EfektKsztalcenia page', async () => {
    await efektKsztalceniaComponentsPage.clickOnCreateButton();
    efektKsztalceniaUpdatePage = new EfektKsztalceniaUpdatePage();
    expect(await efektKsztalceniaUpdatePage.getPageTitle()).to.eq('appApp.efektKsztalcenia.home.createOrEditLabel');
    await efektKsztalceniaUpdatePage.cancel();
  });

  it('should create and save EfektKsztalcenias', async () => {
    const nbButtonsBeforeCreate = await efektKsztalceniaComponentsPage.countDeleteButtons();

    await efektKsztalceniaComponentsPage.clickOnCreateButton();
    await promise.all([
      efektKsztalceniaUpdatePage.setOpisInput('opis'),
      efektKsztalceniaUpdatePage.programStudiowSelectLastOption()
      // efektKsztalceniaUpdatePage.przedmiotSelectLastOption(),
      // efektKsztalceniaUpdatePage.efektMinisterialnySelectLastOption(),
    ]);
    expect(await efektKsztalceniaUpdatePage.getOpisInput()).to.eq('opis', 'Expected Opis value to be equals to opis');
    await efektKsztalceniaUpdatePage.save();
    expect(await efektKsztalceniaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await efektKsztalceniaComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EfektKsztalcenia', async () => {
    const nbButtonsBeforeDelete = await efektKsztalceniaComponentsPage.countDeleteButtons();
    await efektKsztalceniaComponentsPage.clickOnLastDeleteButton();

    efektKsztalceniaDeleteDialog = new EfektKsztalceniaDeleteDialog();
    expect(await efektKsztalceniaDeleteDialog.getDialogTitle()).to.eq('appApp.efektKsztalcenia.delete.question');
    await efektKsztalceniaDeleteDialog.clickOnConfirmButton();

    expect(await efektKsztalceniaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
