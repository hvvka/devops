import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EfektMinisterialnyUpdateComponent } from 'app/entities/efekt-ministerialny/efekt-ministerialny-update.component';
import { EfektMinisterialnyService } from 'app/entities/efekt-ministerialny/efekt-ministerialny.service';
import { EfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';

describe('Component Tests', () => {
  describe('EfektMinisterialny Management Update Component', () => {
    let comp: EfektMinisterialnyUpdateComponent;
    let fixture: ComponentFixture<EfektMinisterialnyUpdateComponent>;
    let service: EfektMinisterialnyService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EfektMinisterialnyUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EfektMinisterialnyUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EfektMinisterialnyUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EfektMinisterialnyService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EfektMinisterialny(123);
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
        const entity = new EfektMinisterialny();
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
