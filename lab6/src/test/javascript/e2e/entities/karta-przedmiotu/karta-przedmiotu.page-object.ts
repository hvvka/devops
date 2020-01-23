import { element, by, ElementFinder } from 'protractor';

export class KartaPrzedmiotuComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-karta-przedmiotu div table .btn-danger'));
  title = element.all(by.css('jhi-karta-przedmiotu div h2#page-heading span')).first();

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

export class KartaPrzedmiotuUpdatePage {
  pageTitle = element(by.id('jhi-karta-przedmiotu-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nazwaInput = element(by.id('field_nazwa'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNazwaInput(nazwa: string): Promise<void> {
    await this.nazwaInput.sendKeys(nazwa);
  }

  async getNazwaInput(): Promise<string> {
    return await this.nazwaInput.getAttribute('value');
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

export class KartaPrzedmiotuDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-kartaPrzedmiotu-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-kartaPrzedmiotu'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
