import { element, by, ElementFinder } from 'protractor';

export class OpiekunPrzedmiotuComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-opiekun-przedmiotu div table .btn-danger'));
  title = element.all(by.css('jhi-opiekun-przedmiotu div h2#page-heading span')).first();

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

export class OpiekunPrzedmiotuUpdatePage {
  pageTitle = element(by.id('jhi-opiekun-przedmiotu-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
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

export class OpiekunPrzedmiotuDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-opiekunPrzedmiotu-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-opiekunPrzedmiotu'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
