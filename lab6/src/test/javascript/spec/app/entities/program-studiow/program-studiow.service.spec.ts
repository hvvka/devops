import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import { ProgramStudiowService } from 'app/entities/program-studiow/program-studiow.service';
import { IProgramStudiow, ProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProfilKsztalcenia } from 'app/shared/model/enumerations/profil-ksztalcenia.model';
import { FormaStudiow } from 'app/shared/model/enumerations/forma-studiow.model';
import { JezykProwadzeniaStudiow } from 'app/shared/model/enumerations/jezyk-prowadzenia-studiow.model';

describe('Service Tests', () => {
  describe('ProgramStudiow Service', () => {
    let injector: TestBed;
    let service: ProgramStudiowService;
    let httpMock: HttpTestingController;
    let elemDefault: IProgramStudiow;
    let expectedResult: IProgramStudiow | IProgramStudiow[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ProgramStudiowService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new ProgramStudiow(
        0,
        ProfilKsztalcenia.OGOLNOAKADEMICKI,
        FormaStudiow.STACJONARNE,
        'AAAAAAA',
        'AAAAAAA',
        'AAAAAAA',
        JezykProwadzeniaStudiow.POLSKI,
        0,
        'AAAAAAA'
      );
    });

    describe('Service methods', () => {
      it('should find an element', () => {
        const returnedFromService = Object.assign({}, elemDefault);
        service
          .find(123)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));

        const req = httpMock.expectOne({ method: 'GET' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(elemDefault);
      });

      it('should create a ProgramStudiow', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new ProgramStudiow())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a ProgramStudiow', () => {
        const returnedFromService = Object.assign(
          {
            profilKsztalcenia: 'BBBBBB',
            formaStudiow: 'BBBBBB',
            kierunek: 'BBBBBB',
            specjalnosc: 'BBBBBB',
            wydzial: 'BBBBBB',
            jezykProwadzeniaStudiow: 'BBBBBB',
            liczbaSemestrow: 1,
            cyklKsztalcenia: 'BBBBBB'
          },
          elemDefault
        );

        const expected = Object.assign({}, returnedFromService);
        service
          .update(expected)
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'PUT' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should return a list of ProgramStudiow', () => {
        const returnedFromService = Object.assign(
          {
            profilKsztalcenia: 'BBBBBB',
            formaStudiow: 'BBBBBB',
            kierunek: 'BBBBBB',
            specjalnosc: 'BBBBBB',
            wydzial: 'BBBBBB',
            jezykProwadzeniaStudiow: 'BBBBBB',
            liczbaSemestrow: 1,
            cyklKsztalcenia: 'BBBBBB'
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .query()
          .pipe(
            take(1),
            map(resp => resp.body)
          )
          .subscribe(body => (expectedResult = body));
        const req = httpMock.expectOne({ method: 'GET' });
        req.flush([returnedFromService]);
        httpMock.verify();
        expect(expectedResult).toContainEqual(expected);
      });

      it('should delete a ProgramStudiow', () => {
        service.delete(123).subscribe(resp => (expectedResult = resp.ok));

        const req = httpMock.expectOne({ method: 'DELETE' });
        req.flush({ status: 200 });
        expect(expectedResult);
      });
    });

    afterEach(() => {
      httpMock.verify();
    });
  });
});
