export interface ITypStudiow {
  id?: number;
  nazwa?: string;
  stopienStudiow?: string;
}

export class TypStudiow implements ITypStudiow {
  constructor(public id?: number, public nazwa?: string, public stopienStudiow?: string) {}
}
