import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { TypStudiowComponent } from 'app/entities/typ-studiow/typ-studiow.component';
import { TypStudiowService } from 'app/entities/typ-studiow/typ-studiow.service';
import { TypStudiow } from 'app/shared/model/typ-studiow.model';

describe('Component Tests', () => {
  describe('TypStudiow Management Component', () => {
    let comp: TypStudiowComponent;
    let fixture: ComponentFixture<TypStudiowComponent>;
    let service: TypStudiowService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TypStudiowComponent],
        providers: []
      })
        .overrideTemplate(TypStudiowComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypStudiowComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypStudiowService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new TypStudiow(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.typStudiows && comp.typStudiows[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
