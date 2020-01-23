export interface IKartaPrzedmiotu {
  id?: number;
  nazwa?: string;
}

export class KartaPrzedmiotu implements IKartaPrzedmiotu {
  constructor(public id?: number, public nazwa?: string) {}
}
