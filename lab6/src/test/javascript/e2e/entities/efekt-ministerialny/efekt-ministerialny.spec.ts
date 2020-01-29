import { browser, ExpectedConditions as ec, promise } from 'protractor';
import { NavBarPage, SignInPage } from '../../page-objects/jhi-page-objects';

import {
  EfektMinisterialnyComponentsPage,
  EfektMinisterialnyDeleteDialog,
  EfektMinisterialnyUpdatePage
} from './efekt-ministerialny.page-object';

const expect = chai.expect;

describe('EfektMinisterialny e2e test', () => {
  let navBarPage: NavBarPage;
  let signInPage: SignInPage;
  let efektMinisterialnyComponentsPage: EfektMinisterialnyComponentsPage;
  let efektMinisterialnyUpdatePage: EfektMinisterialnyUpdatePage;
  let efektMinisterialnyDeleteDialog: EfektMinisterialnyDeleteDialog;

  before(async () => {
    await browser.get('/');
    navBarPage = new NavBarPage();
    signInPage = await navBarPage.getSignInPage();
    await signInPage.autoSignInUsing('admin', 'admin');
    await browser.wait(ec.visibilityOf(navBarPage.entityMenu), 5000);
  });

  it('should load EfektMinisterialnies', async () => {
    await navBarPage.goToEntity('efekt-ministerialny');
    efektMinisterialnyComponentsPage = new EfektMinisterialnyComponentsPage();
    await browser.wait(ec.visibilityOf(efektMinisterialnyComponentsPage.title), 5000);
    expect(await efektMinisterialnyComponentsPage.getTitle()).to.eq('appApp.efektMinisterialny.home.title');
  });

  it('should load create EfektMinisterialny page', async () => {
    await efektMinisterialnyComponentsPage.clickOnCreateButton();
    efektMinisterialnyUpdatePage = new EfektMinisterialnyUpdatePage();
    expect(await efektMinisterialnyUpdatePage.getPageTitle()).to.eq('appApp.efektMinisterialny.home.createOrEditLabel');
    await efektMinisterialnyUpdatePage.cancel();
  });

  it('should create and save EfektMinisterialnies', async () => {
    const nbButtonsBeforeCreate = await efektMinisterialnyComponentsPage.countDeleteButtons();

    await efektMinisterialnyComponentsPage.clickOnCreateButton();
    await promise.all([
      efektMinisterialnyUpdatePage.setKodEfektuInput('kodEfektu'),
      efektMinisterialnyUpdatePage.setPoziomEfektuInput('5'),
      efektMinisterialnyUpdatePage.typEfektuMinisterialnegoSelectLastOption()
    ]);
    expect(await efektMinisterialnyUpdatePage.getKodEfektuInput()).to.eq('kodEfektu', 'Expected KodEfektu value to be equals to kodEfektu');
    expect(await efektMinisterialnyUpdatePage.getPoziomEfektuInput()).to.eq('5', 'Expected poziomEfektu value to be equals to 5');
    await efektMinisterialnyUpdatePage.save();
    expect(await efektMinisterialnyUpdatePage.getSaveButton().isPresent(), 'Expected save button disappear').to.be.false;

    expect(await efektMinisterialnyComponentsPage.countDeleteButtons()).to.eq(
      nbButtonsBeforeCreate + 1,
      'Expected one more entry in the table'
    );
  });

  it('should delete last EfektMinisterialny', async () => {
    const nbButtonsBeforeDelete = await efektMinisterialnyComponentsPage.countDeleteButtons();
    await efektMinisterialnyComponentsPage.clickOnLastDeleteButton();

    efektMinisterialnyDeleteDialog = new EfektMinisterialnyDeleteDialog();
    expect(await efektMinisterialnyDeleteDialog.getDialogTitle()).to.eq('appApp.efektMinisterialny.delete.question');
    await efektMinisterialnyDeleteDialog.clickOnConfirmButton();

    expect(await efektMinisterialnyComponentsPage.countDeleteButtons()).to.eq(nbButtonsBeforeDelete - 1);
  });

  after(async () => {
    await navBarPage.autoSignOut();
  });
});
