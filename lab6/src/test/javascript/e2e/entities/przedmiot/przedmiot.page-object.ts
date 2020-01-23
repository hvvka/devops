import { element, by, ElementFinder } from 'protractor';

export class PrzedmiotComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-przedmiot div table .btn-danger'));
  title = element.all(by.css('jhi-przedmiot div h2#page-heading span')).first();

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

export class PrzedmiotUpdatePage {
  pageTitle = element(by.id('jhi-przedmiot-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  nrSemestruInput = element(by.id('field_nrSemestru'));
  nazwaInput = element(by.id('field_nazwa'));
  kartaPrzedmiotuSelect = element(by.id('field_kartaPrzedmiotu'));
  opiekunPrzedmiotuSelect = element(by.id('field_opiekunPrzedmiotu'));
  programStudiowSelect = element(by.id('field_programStudiow'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setNrSemestruInput(nrSemestru: string): Promise<void> {
    await this.nrSemestruInput.sendKeys(nrSemestru);
  }

  async getNrSemestruInput(): Promise<string> {
    return await this.nrSemestruInput.getAttribute('value');
  }

  async setNazwaInput(nazwa: string): Promise<void> {
    await this.nazwaInput.sendKeys(nazwa);
  }

  async getNazwaInput(): Promise<string> {
    return await this.nazwaInput.getAttribute('value');
  }

  async kartaPrzedmiotuSelectLastOption(): Promise<void> {
    await this.kartaPrzedmiotuSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async kartaPrzedmiotuSelectOption(option: string): Promise<void> {
    await this.kartaPrzedmiotuSelect.sendKeys(option);
  }

  getKartaPrzedmiotuSelect(): ElementFinder {
    return this.kartaPrzedmiotuSelect;
  }

  async getKartaPrzedmiotuSelectedOption(): Promise<string> {
    return await this.kartaPrzedmiotuSelect.element(by.css('option:checked')).getText();
  }

  async opiekunPrzedmiotuSelectLastOption(): Promise<void> {
    await this.opiekunPrzedmiotuSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async opiekunPrzedmiotuSelectOption(option: string): Promise<void> {
    await this.opiekunPrzedmiotuSelect.sendKeys(option);
  }

  getOpiekunPrzedmiotuSelect(): ElementFinder {
    return this.opiekunPrzedmiotuSelect;
  }

  async getOpiekunPrzedmiotuSelectedOption(): Promise<string> {
    return await this.opiekunPrzedmiotuSelect.element(by.css('option:checked')).getText();
  }

  async programStudiowSelectLastOption(): Promise<void> {
    await this.programStudiowSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async programStudiowSelectOption(option: string): Promise<void> {
    await this.programStudiowSelect.sendKeys(option);
  }

  getProgramStudiowSelect(): ElementFinder {
    return this.programStudiowSelect;
  }

  async getProgramStudiowSelectedOption(): Promise<string> {
    return await this.programStudiowSelect.element(by.css('option:checked')).getText();
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

export class PrzedmiotDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-przedmiot-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-przedmiot'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
