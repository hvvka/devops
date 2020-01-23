import { element, by, ElementFinder } from 'protractor';

export class TypStudiowComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-typ-studiow div table .btn-danger'));
  title = element.all(by.css('jhi-typ-studiow div h2#page-heading span')).first();

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

export class TypStudiowUpdatePage {
  pageTitle = element(by.id('jhi-typ-studiow-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nazwaInput = element(by.id('field_nazwa'));
  stopienStudiowInput = element(by.id('field_stopienStudiow'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNazwaInput(nazwa: string): Promise<void> {
    await this.nazwaInput.sendKeys(nazwa);
  }

  async getNazwaInput(): Promise<string> {
    return await this.nazwaInput.getAttribute('value');
  }

  async setStopienStudiowInput(stopienStudiow: string): Promise<void> {
    await this.stopienStudiowInput.sendKeys(stopienStudiow);
  }

  async getStopienStudiowInput(): Promise<string> {
    return await this.stopienStudiowInput.getAttribute('value');
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

export class TypStudiowDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-typStudiow-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-typStudiow'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
