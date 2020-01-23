export interface IOpiekunPrzedmiotu {
  id?: number;
}

export class OpiekunPrzedmiotu implements IOpiekunPrzedmiotu {
  constructor(public id?: number) {}
}
