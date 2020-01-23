import { element, by, ElementFinder } from 'protractor';

export class EfektKsztalceniaComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-efekt-ksztalcenia div table .btn-danger'));
  title = element.all(by.css('jhi-efekt-ksztalcenia div h2#page-heading span')).first();

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

export class EfektKsztalceniaUpdatePage {
  pageTitle = element(by.id('jhi-efekt-ksztalcenia-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  opisInput = element(by.id('field_opis'));
  programStudiowSelect = element(by.id('field_programStudiow'));
  przedmiotSelect = element(by.id('field_przedmiot'));
  efektMinisterialnySelect = element(by.id('field_efektMinisterialny'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setOpisInput(opis: string): Promise<void> {
    await this.opisInput.sendKeys(opis);
  }

  async getOpisInput(): Promise<string> {
    return await this.opisInput.getAttribute('value');
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

  async przedmiotSelectLastOption(): Promise<void> {
    await this.przedmiotSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async przedmiotSelectOption(option: string): Promise<void> {
    await this.przedmiotSelect.sendKeys(option);
  }

  getPrzedmiotSelect(): ElementFinder {
    return this.przedmiotSelect;
  }

  async getPrzedmiotSelectedOption(): Promise<string> {
    return await this.przedmiotSelect.element(by.css('option:checked')).getText();
  }

  async efektMinisterialnySelectLastOption(): Promise<void> {
    await this.efektMinisterialnySelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async efektMinisterialnySelectOption(option: string): Promise<void> {
    await this.efektMinisterialnySelect.sendKeys(option);
  }

  getEfektMinisterialnySelect(): ElementFinder {
    return this.efektMinisterialnySelect;
  }

  async getEfektMinisterialnySelectedOption(): Promise<string> {
    return await this.efektMinisterialnySelect.element(by.css('option:checked')).getText();
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

export class EfektKsztalceniaDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-efektKsztalcenia-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-efektKsztalcenia'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
