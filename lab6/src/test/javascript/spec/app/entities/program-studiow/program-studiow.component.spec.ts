import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { ProgramStudiowComponent } from 'app/entities/program-studiow/program-studiow.component';
import { ProgramStudiowService } from 'app/entities/program-studiow/program-studiow.service';
import { ProgramStudiow } from 'app/shared/model/program-studiow.model';

describe('Component Tests', () => {
  describe('ProgramStudiow Management Component', () => {
    let comp: ProgramStudiowComponent;
    let fixture: ComponentFixture<ProgramStudiowComponent>;
    let service: ProgramStudiowService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ProgramStudiowComponent],
        providers: []
      })
        .overrideTemplate(ProgramStudiowComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramStudiowComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramStudiowService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new ProgramStudiow(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.programStudiows && comp.programStudiows[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
