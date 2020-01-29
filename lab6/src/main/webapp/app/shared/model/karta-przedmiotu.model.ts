import { RodzajPrzedmiotu } from 'app/shared/model/enumerations/rodzaj-przedmiotu.model';

export interface IKartaPrzedmiotu {
  id?: number;
  kodPrzedmiotu?: string;
  nazwa?: string;
  nazwaAng?: string;
  rodzajPrzedmiotu?: RodzajPrzedmiotu;
}

export class KartaPrzedmiotu implements IKartaPrzedmiotu {
  constructor(
    public id?: number,
    public kodPrzedmiotu?: string,
    public nazwa?: string,
    public nazwaAng?: string,
    public rodzajPrzedmiotu?: RodzajPrzedmiotu
  ) {}
}
