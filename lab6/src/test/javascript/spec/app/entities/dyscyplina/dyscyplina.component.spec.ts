import { ComponentFixture, TestBed } from '@angular/core/testing';
import { of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AppTestModule } from '../../../test.module';
import { DyscyplinaComponent } from 'app/entities/dyscyplina/dyscyplina.component';
import { DyscyplinaService } from 'app/entities/dyscyplina/dyscyplina.service';
import { Dyscyplina } from 'app/shared/model/dyscyplina.model';

describe('Component Tests', () => {
  describe('Dyscyplina Management Component', () => {
    let comp: DyscyplinaComponent;
    let fixture: ComponentFixture<DyscyplinaComponent>;
    let service: DyscyplinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [DyscyplinaComponent],
        providers: []
      })
        .overrideTemplate(DyscyplinaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DyscyplinaComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DyscyplinaService);
    });

    it('Should call load all on init', () => {
      // GIVEN
      const headers = new HttpHeaders().append('link', 'link;link');
      spyOn(service, 'query').and.returnValue(
        of(
          new HttpResponse({
            body: [new Dyscyplina(123)],
            headers
          })
        )
      );

      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.dyscyplinas && comp.dyscyplinas[0]).toEqual(jasmine.objectContaining({ id: 123 }));
    });
  });
});
