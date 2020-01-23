import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import { DyscyplinaComponentsPage, DyscyplinaDeleteDialog, DyscyplinaUpdatePage } from './dyscyplina.page-object';

const expect = chai.expect;

describe('Dyscyplina e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let dyscyplinaComponentsPage: DyscyplinaComponentsPage;
  let dyscyplinaUpdatePage: DyscyplinaUpdatePage;
  let dyscyplinaDeleteDialog: DyscyplinaDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load Dyscyplinas', async () => {
    await navBarPage.goToEntity('dyscyplina');
    dyscyplinaComponentsPage = new DyscyplinaComponentsPage();
    await browser.wait(ec.visibilityOf(dyscyplinaComponentsPage.title), 5000);
    expect(await dyscyplinaComponentsPage.getTitle()).to.eq('appApp.dyscyplina.home.title');
  });

  it('should load create Dyscyplina page', async () => {
    await dyscyplinaComponentsPage.clickOnCreateButton();
    dyscyplinaUpdatePage = new DyscyplinaUpdatePage();
    expect(await dyscyplinaUpdatePage.getPageTitle()).to.eq('appApp.dyscyplina.home.createOrEditLabel');
    await dyscyplinaUpdatePage.cancel();
  });

  it('should create and save Dyscyplinas', async () => {
    const nbButtonsBeforeCreate = await dyscyplinaComponentsPage.countDeleteButtons();

    await dyscyplinaComponentsPage.clickOnCreateButton();
    await promise.all([
      dyscyplinaUpdatePage.setNazwaInput('nazwa')
      // dyscyplinaUpdatePage.progamStudiowSelectLastOption(),
    ]);
    expect(await dyscyplinaUpdatePage.getNazwaInput()).to.eq('nazwa', 'Expected Nazwa value to be equals to nazwa');
    const selectedCzyWiodaca = dyscyplinaUpdatePage.getCzyWiodacaInput();
    if (await selectedCzyWiodaca.isSelected()) {
      await dyscyplinaUpdatePage.getCzyWiodacaInput().click();
      expect(await dyscyplinaUpdatePage.getCzyWiodacaInput().isSelected(), 'Expected czyWiodaca not to be selected').to.be.false;
    } else {
      await dyscyplinaUpdatePage.getCzyWiodacaInput().click();
      expect(await dyscyplinaUpdatePage.getCzyWiodacaInput().isSelected(), 'Expected czyWiodaca to be selected').to.be.true;
    }
    await dyscyplinaUpdatePage.save();
    expect(await dyscyplinaUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await dyscyplinaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeCreate + 1, 'Expected one more entry in the table');
  });

  it('should delete last Dyscyplina', async () => {
    const nbButtonsBeforeDelete = await dyscyplinaComponentsPage.countDeleteButtons();
    await dyscyplinaComponentsPage.clickOnLastDeleteButton();

    dyscyplinaDeleteDialog = new DyscyplinaDeleteDialog();
    expect(await dyscyplinaDeleteDialog.getDialogTitle()).to.eq('appApp.dyscyplina.delete.question');
    await dyscyplinaDeleteDialog.clickOnConfirmButton();

    expect(await dyscyplinaComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
