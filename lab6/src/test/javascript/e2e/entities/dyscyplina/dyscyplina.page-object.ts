import { element, by, ElementFinder } from 'protractor';

export class DyscyplinaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-dyscyplina div table .btn-danger'));
  title = element.all(by.css('jhi-dyscyplina div h2#page-heading span')).first();

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

export class DyscyplinaUpdatePage {
  pageTitle = element(by.id('jhi-dyscyplina-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nazwaInput = element(by.id('field_nazwa'));
  czyWiodacaInput = element(by.id('field_czyWiodaca'));
  progamStudiowSelect = element(by.id('field_progamStudiow'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNazwaInput(nazwa: string): Promise<void> {
    await this.nazwaInput.sendKeys(nazwa);
  }

  async getNazwaInput(): Promise<string> {
    return await this.nazwaInput.getAttribute('value');
  }

  getCzyWiodacaInput(): ElementFinder {
    return this.czyWiodacaInput;
  }

  async progamStudiowSelectLastOption(): Promise<void> {
    await this.progamStudiowSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async progamStudiowSelectOption(option: string): Promise<void> {
    await this.progamStudiowSelect.sendKeys(option);
  }

  getProgamStudiowSelect(): ElementFinder {
    return this.progamStudiowSelect;
  }

  async getProgamStudiowSelectedOption(): Promise<string> {
    return await this.progamStudiowSelect.element(by.css('option:checked')).getText();
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

export class DyscyplinaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-dyscyplina-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-dyscyplina'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
