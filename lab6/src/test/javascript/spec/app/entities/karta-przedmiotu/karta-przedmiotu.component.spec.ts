import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { KartaPrzedmiotuComponent } from 'app/entities/karta-przedmiotu/karta-przedmiotu.component';
import { KartaPrzedmiotuService } from 'app/entities/karta-przedmiotu/karta-przedmiotu.service';
import { KartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';

describe('Component Tests', () => {
  describe('KartaPrzedmiotu Management Component', () => {
    let comp: KartaPrzedmiotuComponent;
    let fixture: ComponentFixture<KartaPrzedmiotuComponent>;
    let service: KartaPrzedmiotuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [KartaPrzedmiotuComponent],
        providers: []
      })
        .overrideTemplate(KartaPrzedmiotuComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KartaPrzedmiotuComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KartaPrzedmiotuService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new KartaPrzedmiotu(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.kartaPrzedmiotus && comp.kartaPrzedmiotus[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
