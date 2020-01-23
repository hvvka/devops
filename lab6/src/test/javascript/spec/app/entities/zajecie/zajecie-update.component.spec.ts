import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ZajecieUpdateComponent } from 'app/entities/zajecie/zajecie-update.component';
import { ZajecieService } from 'app/entities/zajecie/zajecie.service';
import { Zajecie } from 'app/shared/model/zajecie.model';

describe('Component Tests', () => {
  describe('Zajecie Management Update Component', () => {
    let comp: ZajecieUpdateComponent;
    let fixture: ComponentFixture<ZajecieUpdateComponent>;
    let service: ZajecieService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ZajecieUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ZajecieUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ZajecieUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ZajecieService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Zajecie(123);
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
        const entity = new Zajecie();
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
