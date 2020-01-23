import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { EfektMinisterialnyComponent } from 'app/entities/efekt-ministerialny/efekt-ministerialny.component';
import { EfektMinisterialnyService } from 'app/entities/efekt-ministerialny/efekt-ministerialny.service';
import { EfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';

describe('Component Tests', () => {
  describe('EfektMinisterialny Management Component', () => {
    let comp: EfektMinisterialnyComponent;
    let fixture: ComponentFixture<EfektMinisterialnyComponent>;
    let service: EfektMinisterialnyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EfektMinisterialnyComponent],
        providers: []
      })
        .overrideTemplate(EfektMinisterialnyComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EfektMinisterialnyComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EfektMinisterialnyService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new EfektMinisterialny(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.efektMinisterialnies && comp.efektMinisterialnies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
