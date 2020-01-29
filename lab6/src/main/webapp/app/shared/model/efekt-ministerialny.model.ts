import { IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';
import { TypEfektuMinisterialnego } from 'app/shared/model/enumerations/typ-efektu-ministerialnego.model';

export interface IEfektMinisterialny {
  id?: number;
  kodEfektu?: string;
  poziomEfektu?: number;
  typEfektuMinisterialnego?: TypEfektuMinisterialnego;
  efektKsztalcenias?: IEfektKsztalcenia[];
}

export class EfektMinisterialny implements IEfektMinisterialny {
  constructor(
    public id?: number,
    public kodEfektu?: string,
    public poziomEfektu?: number,
    public typEfektuMinisterialnego?: TypEfektuMinisterialnego,
    public efektKsztalcenias?: IEfektKsztalcenia[]
  ) {}
}
