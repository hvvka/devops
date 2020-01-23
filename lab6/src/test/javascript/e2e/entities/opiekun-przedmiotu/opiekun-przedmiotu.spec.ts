import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  OpiekunPrzedmiotuComponentsPage,
  OpiekunPrzedmiotuDeleteDialog,
  OpiekunPrzedmiotuUpdatePage
} from './opiekun-przedmiotu.page-object';

const expect = chai.expect;

describe('OpiekunPrzedmiotu e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let opiekunPrzedmiotuComponentsPage: OpiekunPrzedmiotuComponentsPage;
  let opiekunPrzedmiotuUpdatePage: OpiekunPrzedmiotuUpdatePage;
  let opiekunPrzedmiotuDeleteDialog: OpiekunPrzedmiotuDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load OpiekunPrzedmiotus', async () => {
    await navBarPage.goToEntity('opiekun-przedmiotu');
    opiekunPrzedmiotuComponentsPage = new OpiekunPrzedmiotuComponentsPage();
    await browser.wait(ec.visibilityOf(opiekunPrzedmiotuComponentsPage.title), 5000);
    expect(await opiekunPrzedmiotuComponentsPage.getTitle()).to.eq('appApp.opiekunPrzedmiotu.home.title');
  });

  it('should load create OpiekunPrzedmiotu page', async () => {
    await opiekunPrzedmiotuComponentsPage.clickOnCreateButton();
    opiekunPrzedmiotuUpdatePage = new OpiekunPrzedmiotuUpdatePage();
    expect(await opiekunPrzedmiotuUpdatePage.getPageTitle()).to.eq('appApp.opiekunPrzedmiotu.home.createOrEditLabel');
    await opiekunPrzedmiotuUpdatePage.cancel();
  });

  it('should create and save OpiekunPrzedmiotus', async () => {
    const nbButtonsBeforeCreate = await opiekunPrzedmiotuComponentsPage.countDeleteButtons();

    await opiekunPrzedmiotuComponentsPage.clickOnCreateButton();
    await promise.all([]);
    await opiekunPrzedmiotuUpdatePage.save();
    expect(await opiekunPrzedmiotuUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await opiekunPrzedmiotuComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last OpiekunPrzedmiotu', async () => {
    const nbButtonsBeforeDelete = await opiekunPrzedmiotuComponentsPage.countDeleteButtons();
    await opiekunPrzedmiotuComponentsPage.clickOnLastDeleteButton();

    opiekunPrzedmiotuDeleteDialog = new OpiekunPrzedmiotuDeleteDialog();
    expect(await opiekunPrzedmiotuDeleteDialog.getDialogTitle()).to.eq('appApp.opiekunPrzedmiotu.delete.question');
    await opiekunPrzedmiotuDeleteDialog.clickOnConfirmButton();

    expect(await opiekunPrzedmiotuComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
