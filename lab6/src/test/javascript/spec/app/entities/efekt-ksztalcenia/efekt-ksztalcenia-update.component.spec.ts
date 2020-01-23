import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { EfektKsztalceniaUpdateComponent } from 'app/entities/efekt-ksztalcenia/efekt-ksztalcenia-update.component';
import { EfektKsztalceniaService } from 'app/entities/efekt-ksztalcenia/efekt-ksztalcenia.service';
import { EfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';

describe('Component Tests', () => {
  describe('EfektKsztalcenia Management Update Component', () => {
    let comp: EfektKsztalceniaUpdateComponent;
    let fixture: ComponentFixture<EfektKsztalceniaUpdateComponent>;
    let service: EfektKsztalceniaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EfektKsztalceniaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(EfektKsztalceniaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(EfektKsztalceniaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EfektKsztalceniaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new EfektKsztalcenia(123);
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
        const entity = new EfektKsztalcenia();
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
