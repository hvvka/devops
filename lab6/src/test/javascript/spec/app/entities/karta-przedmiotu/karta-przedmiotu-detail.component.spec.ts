import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { KartaPrzedmiotuDetailComponent } from 'app/entities/karta-przedmiotu/karta-przedmiotu-detail.component';
import { KartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';

describe('Component Tests', () => {
  describe('KartaPrzedmiotu Management Detail Component', () => {
    let comp: KartaPrzedmiotuDetailComponent;
    let fixture: ComponentFixture<KartaPrzedmiotuDetailComponent>;
    const route = ({ data: of({ kartaPrzedmiotu: new KartaPrzedmiotu(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [KartaPrzedmiotuDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(KartaPrzedmiotuDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(KartaPrzedmiotuDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load kartaPrzedmiotu on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.kartaPrzedmiotu).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
