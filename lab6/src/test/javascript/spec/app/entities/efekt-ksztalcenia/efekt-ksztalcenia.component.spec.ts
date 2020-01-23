import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { EfektKsztalceniaComponent } from 'app/entities/efekt-ksztalcenia/efekt-ksztalcenia.component';
import { EfektKsztalceniaService } from 'app/entities/efekt-ksztalcenia/efekt-ksztalcenia.service';
import { EfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';

describe('Component Tests', () => {
  describe('EfektKsztalcenia Management Component', () => {
    let comp: EfektKsztalceniaComponent;
    let fixture: ComponentFixture<EfektKsztalceniaComponent>;
    let service: EfektKsztalceniaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EfektKsztalceniaComponent],
        providers: []
      })
        .overrideTemplate(EfektKsztalceniaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EfektKsztalceniaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EfektKsztalceniaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EfektKsztalcenia(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.efektKsztalcenias && comp.efektKsztalcenias[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
