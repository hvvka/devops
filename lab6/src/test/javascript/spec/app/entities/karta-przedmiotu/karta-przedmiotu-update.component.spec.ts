import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { KartaPrzedmiotuUpdateComponent } from 'app/entities/karta-przedmiotu/karta-przedmiotu-update.component';
import { KartaPrzedmiotuService } from 'app/entities/karta-przedmiotu/karta-przedmiotu.service';
import { KartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';

describe('Component Tests', () => {
  describe('KartaPrzedmiotu Management Update Component', () => {
    let comp: KartaPrzedmiotuUpdateComponent;
    let fixture: ComponentFixture<KartaPrzedmiotuUpdateComponent>;
    let service: KartaPrzedmiotuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [KartaPrzedmiotuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(KartaPrzedmiotuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KartaPrzedmiotuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(KartaPrzedmiotuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new KartaPrzedmiotu(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new KartaPrzedmiotu();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
