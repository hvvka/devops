import { by, element, ElementFinder } from 'protractor';

export class EfektMinisterialnyComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-efekt-ministerialny div table .btn-danger'));
  title = element.all(by.css('jhi-efekt-ministerialny div h2#page-heading span')).first();

  async clickOnCreateButton(): Promise<void> {
    await this.createButton.click();
  }

  async clickOnLastDeleteButton(): Promise<void> {
    await this.deleteButtons.last().click();
  }

  async countDeleteButtons(): Promise<number> {
    return this.deleteButtons.count();
  }

  async getTitle(): Promise<string> {
    return this.title.getAttribute('jhiTranslate');
  }
}

export class EfektMinisterialnyUpdatePage {
  pageTitle = element(by.id('jhi-efekt-ministerialny-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  kodEfektuInput = element(by.id('field_kodEfektu'));
  poziomEfektuInput = element(by.id('field_poziomEfektu'));
  typEfektuMinisterialnegoSelect = element(by.id('field_typEfektuMinisterialnego'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setKodEfektuInput(kodEfektu: string): Promise<void> {
    await this.kodEfektuInput.sendKeys(kodEfektu);
  }

  async getKodEfektuInput(): Promise<string> {
    return await this.kodEfektuInput.getAttribute('value');
  }

  async setPoziomEfektuInput(poziomEfektu: string): Promise<void> {
    await this.poziomEfektuInput.sendKeys(poziomEfektu);
  }

  async getPoziomEfektuInput(): Promise<string> {
    return await this.poziomEfektuInput.getAttribute('value');
  }

  async setTypEfektuMinisterialnegoSelect(typEfektuMinisterialnego: string): Promise<void> {
    await this.typEfektuMinisterialnegoSelect.sendKeys(typEfektuMinisterialnego);
  }

  async getTypEfektuMinisterialnegoSelect(): Promise<string> {
    return await this.typEfektuMinisterialnegoSelect.element(by.css('option:checked')).getText();
  }

  async typEfektuMinisterialnegoSelectLastOption(): Promise<void> {
    await this.typEfektuMinisterialnegoSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async save(): Promise<void> {
    await this.saveButton.click();
  }

  async cancel(): Promise<void> {
    await this.cancelButton.click();
  }

  getSaveButton(): ElementFinder {
    return this.saveButton;
  }
}

export class EfektMinisterialnyDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-efektMinisterialny-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-efektMinisterialny'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
