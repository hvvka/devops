import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ProgramStudiowDetailComponent } from 'app/entities/program-studiow/program-studiow-detail.component';
import { ProgramStudiow } from 'app/shared/model/program-studiow.model';

describe('Component Tests', () => {
  describe('ProgramStudiow Management Detail Component', () => {
    let comp: ProgramStudiowDetailComponent;
    let fixture: ComponentFixture<ProgramStudiowDetailComponent>;
    const route = ({ data: of({ programStudiow: new ProgramStudiow(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ProgramStudiowDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ProgramStudiowDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ProgramStudiowDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load programStudiow on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.programStudiow).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
