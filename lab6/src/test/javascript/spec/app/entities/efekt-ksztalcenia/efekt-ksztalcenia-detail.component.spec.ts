import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EfektKsztalceniaDetailComponent } from 'app/entities/efekt-ksztalcenia/efekt-ksztalcenia-detail.component';
import { EfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';

describe('Component Tests', () => {
  describe('EfektKsztalcenia Management Detail Component', () => {
    let comp: EfektKsztalceniaDetailComponent;
    let fixture: ComponentFixture<EfektKsztalceniaDetailComponent>;
    const route = ({ data: of({ efektKsztalcenia: new EfektKsztalcenia(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EfektKsztalceniaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(EfektKsztalceniaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EfektKsztalceniaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load efektKsztalcenia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.efektKsztalcenia).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
