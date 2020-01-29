import { IKartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';
import { IOpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';
import { IProgramStudiow } from 'app/shared/model/program-studiow.model';
import { IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';

export interface IPrzedmiot {
  id?: number;
  nrSemestru?: number;
  nazwa?: string;
  kartaPrzedmiotu?: IKartaPrzedmiotu;
  opiekunPrzedmiotu?: IOpiekunPrzedmiotu;
  programStudiow?: IProgramStudiow;
  efektKsztalcenias?: IEfektKsztalcenia[];
}

export class Przedmiot implements IPrzedmiot {
  constructor(
    public id?: number,
    public nrSemestru?: number,
    public nazwa?: string,
    public kartaPrzedmiotu?: IKartaPrzedmiotu,
    public opiekunPrzedmiotu?: IOpiekunPrzedmiotu,
    public programStudiow?: IProgramStudiow,
    public efektKsztalcenias?: IEfektKsztalcenia[]
  ) {}
}
