import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { ProgramStudiowUpdateComponent } from 'app/entities/program-studiow/program-studiow-update.component';
import { ProgramStudiowService } from 'app/entities/program-studiow/program-studiow.service';
import { ProgramStudiow } from 'app/shared/model/program-studiow.model';

describe('Component Tests', () => {
  describe('ProgramStudiow Management Update Component', () => {
    let comp: ProgramStudiowUpdateComponent;
    let fixture: ComponentFixture<ProgramStudiowUpdateComponent>;
    let service: ProgramStudiowService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [ProgramStudiowUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(ProgramStudiowUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ProgramStudiowUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(ProgramStudiowService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new ProgramStudiow(123);
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
        const entity = new ProgramStudiow();
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
