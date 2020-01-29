import { ITypStudiow } from 'app/shared/model/typ-studiow.model';
import { IDyscyplina } from 'app/shared/model/dyscyplina.model';
import { ProfilKsztalcenia } from 'app/shared/model/enumerations/profil-ksztalcenia.model';
import { FormaStudiow } from 'app/shared/model/enumerations/forma-studiow.model';
import { JezykProwadzeniaStudiow } from 'app/shared/model/enumerations/jezyk-prowadzenia-studiow.model';

export interface IProgramStudiow {
  id?: number;
  profilKsztalcenia?: ProfilKsztalcenia;
  formaStudiow?: FormaStudiow;
  kierunek?: string;
  specjalnosc?: string;
  wydzial?: string;
  jezykProwadzeniaStudiow?: JezykProwadzeniaStudiow;
  liczbaSemestrow?: number;
  cyklKsztalcenia?: string;
  typStudiow?: ITypStudiow;
  dyscyplinas?: IDyscyplina[];
}

export class ProgramStudiow implements IProgramStudiow {
  constructor(
    public id?: number,
    public profilKsztalcenia?: ProfilKsztalcenia,
    public formaStudiow?: FormaStudiow,
    public kierunek?: string,
    public specjalnosc?: string,
    public wydzial?: string,
    public jezykProwadzeniaStudiow?: JezykProwadzeniaStudiow,
    public liczbaSemestrow?: number,
    public cyklKsztalcenia?: string,
    public typStudiow?: ITypStudiow,
    public dyscyplinas?: IDyscyplina[]
  ) {}
}
