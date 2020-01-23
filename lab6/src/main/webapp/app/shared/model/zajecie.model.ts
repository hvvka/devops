import { IZajecie } from 'app/shared/model/zajecie.model';
import { IPrzedmiot } from 'app/shared/model/przedmiot.model';
import { FormaPrzedmiotu } from 'app/shared/model/enumerations/forma-przedmiotu.model';
import { ModulKsztalcenia } from 'app/shared/model/enumerations/modul-ksztalcenia.model';
import { PoziomJezyka } from 'app/shared/model/enumerations/poziom-jezyka.model';

export interface IZajecie {
  id?: number;
  forma?: FormaPrzedmiotu;
  eCTS?: number;
  zZU?: number;
  cNPS?: number;
  modulKsztalcenia?: ModulKsztalcenia;
  poziomJezyka?: PoziomJezyka;
  formaWiodaca?: IZajecie;
  grupaKursow?: IZajecie;
  przedmiot?: IPrzedmiot;
}

export class Zajecie implements IZajecie {
  constructor(
    public id?: number,
    public forma?: FormaPrzedmiotu,
    public eCTS?: number,
    public zZU?: number,
    public cNPS?: number,
    public modulKsztalcenia?: ModulKsztalcenia,
    public poziomJezyka?: PoziomJezyka,
    public formaWiodaca?: IZajecie,
    public grupaKursow?: IZajecie,
    public przedmiot?: IPrzedmiot
  ) {}
}
