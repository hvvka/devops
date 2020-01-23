import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { of } from 'rxjs';

import { AppTestModule } from '../../../test.module';
import { DyscyplinaUpdateComponent } from 'app/entities/dyscyplina/dyscyplina-update.component';
import { DyscyplinaService } from 'app/entities/dyscyplina/dyscyplina.service';
import { Dyscyplina } from 'app/shared/model/dyscyplina.model';

describe('Component Tests', () => {
  describe('Dyscyplina Management Update Component', () => {
    let comp: DyscyplinaUpdateComponent;
    let fixture: ComponentFixture<DyscyplinaUpdateComponent>;
    let service: DyscyplinaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [DyscyplinaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(DyscyplinaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DyscyplinaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(DyscyplinaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Dyscyplina(123);
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
        const entity = new Dyscyplina();
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
