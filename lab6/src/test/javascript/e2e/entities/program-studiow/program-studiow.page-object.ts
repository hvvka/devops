import { by, element, ElementFinder } from 'protractor';

export class ProgramStudiowComponentsPage {
  createButton = element(by.id('jh-create-entity'));
  deleteButtons = element.all(by.css('jhi-program-studiow div table .btn-danger'));
  title = element.all(by.css('jhi-program-studiow div h2#page-heading span')).first();

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

export class ProgramStudiowUpdatePage {
  pageTitle = element(by.id('jhi-program-studiow-heading'));
  saveButton = element(by.id('save-entity'));
  cancelButton = element(by.id('cancel-save'));
  profilKsztalceniaSelect = element(by.id('field_profilKsztalcenia'));
  formaStudiowSelect = element(by.id('field_formaStudiow'));
  kierunekInput = element(by.id('field_kierunek'));
  specjalnoscInput = element(by.id('field_specjalnosc'));
  wydzialInput = element(by.id('field_wydzial'));
  jezykProwadzeniaStudiowSelect = element(by.id('field_jezykProwadzeniaStudiow'));
  liczbaSemestrowInput = element(by.id('field_liczbaSemestrow'));
  cyklKsztalceniaInput = element(by.id('field_cyklKsztalcenia'));
  typStudiowSelect = element(by.id('field_typStudiow'));

  async getPageTitle(): Promise<string> {
    return this.pageTitle.getAttribute('jhiTranslate');
  }

  async setProfilKsztalceniaSelect(profilKsztalcenia: string): Promise<void> {
    await this.profilKsztalceniaSelect.sendKeys(profilKsztalcenia);
  }

  async getProfilKsztalceniaSelect(): Promise<string> {
    return await this.profilKsztalceniaSelect.element(by.css('option:checked')).getText();
  }

  async profilKsztalceniaSelectLastOption(): Promise<void> {
    await this.profilKsztalceniaSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setFormaStudiowSelect(formaStudiow: string): Promise<void> {
    await this.formaStudiowSelect.sendKeys(formaStudiow);
  }

  async getFormaStudiowSelect(): Promise<string> {
    return await this.formaStudiowSelect.element(by.css('option:checked')).getText();
  }

  async formaStudiowSelectLastOption(): Promise<void> {
    await this.formaStudiowSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setKierunekInput(kierunek: string): Promise<void> {
    await this.kierunekInput.sendKeys(kierunek);
  }

  async getKierunekInput(): Promise<string> {
    return await this.kierunekInput.getAttribute('value');
  }

  async setSpecjalnoscInput(specjalnosc: string): Promise<void> {
    await this.specjalnoscInput.sendKeys(specjalnosc);
  }

  async getSpecjalnoscInput(): Promise<string> {
    return await this.specjalnoscInput.getAttribute('value');
  }

  async setWydzialInput(wydzial: string): Promise<void> {
    await this.wydzialInput.sendKeys(wydzial);
  }

  async getWydzialInput(): Promise<string> {
    return await this.wydzialInput.getAttribute('value');
  }

  async setJezykProwadzeniaStudiowSelect(jezykProwadzeniaStudiow: string): Promise<void> {
    await this.jezykProwadzeniaStudiowSelect.sendKeys(jezykProwadzeniaStudiow);
  }

  async getJezykProwadzeniaStudiowSelect(): Promise<string> {
    return await this.jezykProwadzeniaStudiowSelect.element(by.css('option:checked')).getText();
  }

  async jezykProwadzeniaStudiowSelectLastOption(): Promise<void> {
    await this.jezykProwadzeniaStudiowSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async setLiczbaSemestrowInput(liczbaSemestrow: string): Promise<void> {
    await this.liczbaSemestrowInput.sendKeys(liczbaSemestrow);
  }

  async getLiczbaSemestrowInput(): Promise<string> {
    return await this.liczbaSemestrowInput.getAttribute('value');
  }

  async setCyklKsztalceniaInput(cyklKsztalcenia: string): Promise<void> {
    await this.cyklKsztalceniaInput.sendKeys(cyklKsztalcenia);
  }

  async getCyklKsztalceniaInput(): Promise<string> {
    return await this.cyklKsztalceniaInput.getAttribute('value');
  }

  async typStudiowSelectLastOption(): Promise<void> {
    await this.typStudiowSelect
      .all(by.tagName('option'))
      .last()
      .click();
  }

  async typStudiowSelectOption(option: string): Promise<void> {
    await this.typStudiowSelect.sendKeys(option);
  }

  getTypStudiowSelect(): ElementFinder {
    return this.typStudiowSelect;
  }

  async getTypStudiowSelectedOption(): Promise<string> {
    return await this.typStudiowSelect.element(by.css('option:checked')).getText();
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

export class ProgramStudiowDeleteDialog {
  private dialogTitle = element(by.id('jhi-delete-programStudiow-heading'));
  private confirmButton = element(by.id('jhi-confirm-delete-programStudiow'));

  async getDialogTitle(): Promise<string> {
    return this.dialogTitle.getAttribute('jhiTranslate');
  }

  async clickOnConfirmButton(): Promise<void> {
    await this.confirmButton.click();
  }
}
