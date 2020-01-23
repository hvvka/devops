import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ZajecieDetailComponent } from 'app/entities/zajecie/zajecie-detail.component';
import { Zajecie } from 'app/shared/model/zajecie.model';

describe('Component Tests', () => {
  describe('Zajecie Management Detail Component', () => {
    let comp: ZajecieDetailComponent;
    let fixture: ComponentFixture<ZajecieDetailComponent>;
    const route = ({ data: of({ zajecie: new Zajecie(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ZajecieDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(ZajecieDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ZajecieDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load zajecie on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.zajecie).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
