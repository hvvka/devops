import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { OpiekunPrzedmiotuUpdateComponent } from 'app/entities/opiekun-przedmiotu/opiekun-przedmiotu-update.component';
import { OpiekunPrzedmiotuService } from 'app/entities/opiekun-przedmiotu/opiekun-przedmiotu.service';
import { OpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';

describe('Component Tests', () => {
  describe('OpiekunPrzedmiotu Management Update Component', () => {
    let comp: OpiekunPrzedmiotuUpdateComponent;
    let fixture: ComponentFixture<OpiekunPrzedmiotuUpdateComponent>;
    let service: OpiekunPrzedmiotuService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [OpiekunPrzedmiotuUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(OpiekunPrzedmiotuUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(OpiekunPrzedmiotuUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(OpiekunPrzedmiotuService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new OpiekunPrzedmiotu(123);
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
        const entity = new OpiekunPrzedmiotu();
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
