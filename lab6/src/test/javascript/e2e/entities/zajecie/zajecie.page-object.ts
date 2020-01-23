import { element, by, ElementFinder } from 'protractor';

export class ZajecieComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-zajecie div table .btn-danger'));
  title = element.all(by.css('jhi-zajecie div h2#page-heading span')).first();

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

export class ZajecieUpdatePage {
  pageTitle = element(by.id('jhi-zajecie-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  formaSelect = element(by.id('field_forma'));
  eCTSInput = element(by.id('field_eCTS'));
  zZUInput = element(by.id('field_zZU'));
  cNPSInput = element(by.id('field_cNPS'));
  modulKsztalceniaSelect = element(by.id('field_modulKsztalcenia'));
  poziomJezykaSelect = element(by.id('field_poziomJezyka'));
  formaWiodacaSelect = element(by.id('field_formaWiodaca'));
  grupaKursowSelect = element(by.id('field_grupaKursow'));
  przedmiotSelect = element(by.id('field_przedmiot'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setFormaSelect(forma: string): Promise<void> {
    await this.formaSelect.sendKeys(forma);
  }

  async getFormaSelect(): Promise<string> {
    return await this.formaSelect.element(by.css('option:checked')).getText();
  }

  async formaSelectLastOption(): Promise<void> {
    await this.formaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setECTSInput(eCTS: string): Promise<void> {
    await this.eCTSInput.sendKeys(eCTS);
  }

  async getECTSInput(): Promise<string> {
    return await this.eCTSInput.getAttribute('value');
  }

  async setZZUInput(zZU: string): Promise<void> {
    await this.zZUInput.sendKeys(zZU);
  }

  async getZZUInput(): Promise<string> {
    return await this.zZUInput.getAttribute('value');
  }

  async setCNPSInput(cNPS: string): Promise<void> {
    await this.cNPSInput.sendKeys(cNPS);
  }

  async getCNPSInput(): Promise<string> {
    return await this.cNPSInput.getAttribute('value');
  }

  async setModulKsztalceniaSelect(modulKsztalcenia: string): Promise<void> {
    await this.modulKsztalceniaSelect.sendKeys(modulKsztalcenia);
  }

  async getModulKsztalceniaSelect(): Promise<string> {
    return await this.modulKsztalceniaSelect.element(by.css('option:checked')).getText();
  }

  async modulKsztalceniaSelectLastOption(): Promise<void> {
    await this.modulKsztalceniaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setPoziomJezykaSelect(poziomJezyka: string): Promise<void> {
    await this.poziomJezykaSelect.sendKeys(poziomJezyka);
  }

  async getPoziomJezykaSelect(): Promise<string> {
    return await this.poziomJezykaSelect.element(by.css('option:checked')).getText();
  }

  async poziomJezykaSelectLastOption(): Promise<void> {
    await this.poziomJezykaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formaWiodacaSelectLastOption(): Promise<void> {
    await this.formaWiodacaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async formaWiodacaSelectOption(option: string): Promise<void> {
    await this.formaWiodacaSelect.sendKeys(option);
  }

  getFormaWiodacaSelect(): ElementFinder {
    return this.formaWiodacaSelect;
  }

  async getFormaWiodacaSelectedOption(): Promise<string> {
    return await this.formaWiodacaSelect.element(by.css('option:checked')).getText();
  }

  async grupaKursowSelectLastOption(): Promise<void> {
    await this.grupaKursowSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async grupaKursowSelectOption(option: string): Promise<void> {
    await this.grupaKursowSelect.sendKeys(option);
  }

  getGrupaKursowSelect(): ElementFinder {
    return this.grupaKursowSelect;
  }

  async getGrupaKursowSelectedOption(): Promise<string> {
    return await this.grupaKursowSelect.element(by.css('option:checked')).getText();
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

export class ZajecieDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-zajecie-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-zajecie'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
