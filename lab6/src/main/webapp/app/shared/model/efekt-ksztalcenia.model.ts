import { IProgramStudiow } from 'app/shared/model/program-studiow.model';
import { IPrzedmiot } from 'app/shared/model/przedmiot.model';
import { IEfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';

export interface IEfektKsztalcenia {
  id?: number;
  kodEfektu?: string;
  opis?: string;
  programStudiow?: IProgramStudiow;
  przedmiots?: IPrzedmiot[];
  efektMinisterialnies?: IEfektMinisterialny[];
}

export class EfektKsztalcenia implements IEfektKsztalcenia {
  constructor(
    public id?: number,
    public kodEfektu?: string,
    public opis?: string,
    public programStudiow?: IProgramStudiow,
    public przedmiots?: IPrzedmiot[],
    public efektMinisterialnies?: IEfektMinisterialny[]
  ) {}
}
