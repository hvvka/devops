import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EfektMinisterialnyDetailComponent } from 'app/entities/efekt-ministerialny/efekt-ministerialny-detail.component';
import { EfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';

describe('Component Tests', () => {
  describe('EfektMinisterialny Management Detail Component', () => {
    let comp: EfektMinisterialnyDetailComponent;
    let fixture: ComponentFixture<EfektMinisterialnyDetailComponent>;
    const route = ({ data: of({ efektMinisterialny: new EfektMinisterialny(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EfektMinisterialnyDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EfektMinisterialnyDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EfektMinisterialnyDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load efektMinisterialny on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.efektMinisterialny).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
