import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { OpiekunPrzedmiotuComponent } from 'app/entities/opiekun-przedmiotu/opiekun-przedmiotu.component';
import { OpiekunPrzedmiotuService } from 'app/entities/opiekun-przedmiotu/opiekun-przedmiotu.service';
import { OpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';

describe('Component Tests', () => {
  describe('OpiekunPrzedmiotu Management Component', () => {
    let comp: OpiekunPrzedmiotuComponent;
    let fixture: ComponentFixture<OpiekunPrzedmiotuComponent>;
    let service: OpiekunPrzedmiotuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [OpiekunPrzedmiotuComponent],
        providers: []
      })
        .overrideTemplate(OpiekunPrzedmiotuComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpiekunPrzedmiotuComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpiekunPrzedmiotuService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new OpiekunPrzedmiotu(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.opiekunPrzedmiotus && comp.opiekunPrzedmiotus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
