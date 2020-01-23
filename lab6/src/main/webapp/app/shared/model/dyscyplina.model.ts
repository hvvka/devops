import { IProgramStudiow } from 'app/shared/model/program-studiow.model';

export interface IDyscyplina {
  id?: number;
  nazwa?: string;
  czyWiodaca?: boolean;
  progamStudiows?: IProgramStudiow[];
}

export class Dyscyplina implements IDyscyplina {
  constructor(public id?: number, public nazwa?: string, public czyWiodaca?: boolean, public progamStudiows?: IProgramStudiow[]) {
    this.czyWiodaca = this.czyWiodaca || false;
  }
}
