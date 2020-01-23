import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { DyscyplinaDetailComponent } from 'app/entities/dyscyplina/dyscyplina-detail.component';
import { Dyscyplina } from 'app/shared/model/dyscyplina.model';

describe('Component Tests', () => {
  describe('Dyscyplina Management Detail Component', () => {
    let comp: DyscyplinaDetailComponent;
    let fixture: ComponentFixture<DyscyplinaDetailComponent>;
    const route = ({ data: of({ dyscyplina: new Dyscyplina(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [DyscyplinaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(DyscyplinaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DyscyplinaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load dyscyplina on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.dyscyplina).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
