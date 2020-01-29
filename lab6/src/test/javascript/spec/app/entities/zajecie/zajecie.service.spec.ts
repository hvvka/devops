import { getTestBed, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { map, take } from 'rxjs/operators';
import { ZajecieService } from 'app/entities/zajecie/zajecie.service';
import { IZajecie, Zajecie } from 'app/shared/model/zajecie.model';
import { FormaPrzedmiotu } from 'app/shared/model/enumerations/forma-przedmiotu.model';
import { ModulKsztalcenia } from 'app/shared/model/enumerations/modul-ksztalcenia.model';
import { PoziomJezyka } from 'app/shared/model/enumerations/poziom-jezyka.model';
import { FormaZaliczenia } from 'app/shared/model/enumerations/forma-zaliczenia.model';

describe('Service Tests', () => {
  describe('Zajecie Service', () => {
    let injector: TestBed;
    let service: ZajecieService;
    let httpMock: HttpTestingController;
    let elemDefault: IZajecie;
    let expectedResult: IZajecie | IZajecie[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(ZajecieService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new Zajecie(
        0,
        FormaPrzedmiotu.WYKLAD,
        0,
        0,
        0,
        ModulKsztalcenia.PROFILOWY,
        PoziomJezyka.NIE_DOTYCZY,
        FormaZaliczenia.EGZAMIN,
        false
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

      it('should create a Zajecie', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new Zajecie())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a Zajecie', () => {
        const returnedFromService = Object.assign(
          {
            forma: 'BBBBBB',
            eCTS: 1,
            zZU: 1,
            cNPS: 1,
            modulKsztalcenia: 'BBBBBB',
            poziomJezyka: 'BBBBBB',
            formaZaliczenia: 'BBBBBB',
            czyKoncowy: true
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

      it('should return a list of Zajecie', () => {
        const returnedFromService = Object.assign(
          {
            forma: 'BBBBBB',
            eCTS: 1,
            zZU: 1,
            cNPS: 1,
            modulKsztalcenia: 'BBBBBB',
            poziomJezyka: 'BBBBBB',
            formaZaliczenia: 'BBBBBB',
            czyKoncowy: true
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

      it('should delete a Zajecie', () => {
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
