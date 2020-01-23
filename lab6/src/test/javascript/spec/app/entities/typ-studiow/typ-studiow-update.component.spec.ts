import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { TypStudiowUpdateComponent } from 'app/entities/typ-studiow/typ-studiow-update.component';
import { TypStudiowService } from 'app/entities/typ-studiow/typ-studiow.service';
import { TypStudiow } from 'app/shared/model/typ-studiow.model';

describe('Component Tests', () => {
  describe('TypStudiow Management Update Component', () => {
    let comp: TypStudiowUpdateComponent;
    let fixture: ComponentFixture<TypStudiowUpdateComponent>;
    let service: TypStudiowService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [TypStudiowUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TypStudiowUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TypStudiowUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TypStudiowService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TypStudiow(123);
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
        const entity = new TypStudiow();
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
