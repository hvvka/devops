import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TypStudiowDetailComponent } from 'app/entities/typ-studiow/typ-studiow-detail.component';
import { TypStudiow } from 'app/shared/model/typ-studiow.model';

describe('Component Tests', () => {
  describe('TypStudiow Management Detail Component', () => {
    let comp: TypStudiowDetailComponent;
    let fixture: ComponentFixture<TypStudiowDetailComponent>;
    const route = ({ data: of({ typStudiow: new TypStudiow(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TypStudiowDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TypStudiowDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TypStudiowDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load typStudiow on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.typStudiow).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
