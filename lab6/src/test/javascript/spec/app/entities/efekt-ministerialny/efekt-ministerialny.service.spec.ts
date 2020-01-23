import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import { EfektMinisterialnyService } from 'app/entities/efekt-ministerialny/efekt-ministerialny.service';
import { IEfektMinisterialny, EfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';
import { TypEfektuMinisterialnego } from 'app/shared/model/enumerations/typ-efektu-ministerialnego.model';

describe('Service Tests', () => {
  describe('EfektMinisterialny Service', () => {
    let injector: TestBed;
    let service: EfektMinisterialnyService;
    let httpMock: HttpTestingController;
    let elemDefault: IEfektMinisterialny;
    let expectedResult: IEfektMinisterialny | IEfektMinisterialny[] | boolean | null;
    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule]
      });
      expectedResult = null;
      injector = getTestBed();
      service = injector.get(EfektMinisterialnyService);
      httpMock = injector.get(HttpTestingController);

      elemDefault = new EfektMinisterialny(0, 0, TypEfektuMinisterialnego.OGOLNEGO_KSZTALCENIA);
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

      it('should create a EfektMinisterialny', () => {
        const returnedFromService = Object.assign(
          {
            id: 0
          },
          elemDefault
        );
        const expected = Object.assign({}, returnedFromService);
        service
          .create(new EfektMinisterialny())
          .pipe(take(1))
          .subscribe(resp => (expectedResult = resp.body));
        const req = httpMock.expectOne({ method: 'POST' });
        req.flush(returnedFromService);
        expect(expectedResult).toMatchObject(expected);
      });

      it('should update a EfektMinisterialny', () => {
        const returnedFromService = Object.assign(
          {
            poziomEfektu: 1,
            typEfektuMinisterialnego: 'BBBBBB'
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

      it('should return a list of EfektMinisterialny', () => {
        const returnedFromService = Object.assign(
          {
            poziomEfektu: 1,
            typEfektuMinisterialnego: 'BBBBBB'
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

      it('should delete a EfektMinisterialny', () => {
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
