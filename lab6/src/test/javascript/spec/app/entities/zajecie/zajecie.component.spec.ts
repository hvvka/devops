import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { ZajecieComponent } from 'app/entities/zajecie/zajecie.component';
import { ZajecieService } from 'app/entities/zajecie/zajecie.service';
import { Zajecie } from 'app/shared/model/zajecie.model';

describe('Component Tests', () => {
  describe('Zajecie Management Component', () => {
    let comp: ZajecieComponent;
    let fixture: ComponentFixture<ZajecieComponent>;
    let service: ZajecieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ZajecieComponent],
        providers: []
      })
        .overrideTemplate(ZajecieComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ZajecieComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZajecieService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Zajecie(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.zajecies && comp.zajecies[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
