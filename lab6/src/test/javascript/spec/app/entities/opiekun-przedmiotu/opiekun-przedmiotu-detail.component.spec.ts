import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { OpiekunPrzedmiotuDetailComponent } from 'app/entities/opiekun-przedmiotu/opiekun-przedmiotu-detail.component';
import { OpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';

describe('Component Tests', () => {
  describe('OpiekunPrzedmiotu Management Detail Component', () => {
    let comp: OpiekunPrzedmiotuDetailComponent;
    let fixture: ComponentFixture<OpiekunPrzedmiotuDetailComponent>;
    const route = ({ data: of({ opiekunPrzedmiotu: new OpiekunPrzedmiotu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [OpiekunPrzedmiotuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(OpiekunPrzedmiotuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(OpiekunPrzedmiotuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load opiekunPrzedmiotu on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.opiekunPrzedmiotu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
