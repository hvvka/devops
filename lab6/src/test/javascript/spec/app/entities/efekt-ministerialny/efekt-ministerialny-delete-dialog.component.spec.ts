import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AppTestModule } from '../../../test.module';
import { MockEventManager } from '../../../helpers/mock-event-manager.service';
import { MockActiveModal } from '../../../helpers/mock-active-modal.service';
import { EfektMinisterialnyDeleteDialogComponent } from 'app/entities/efekt-ministerialny/efekt-ministerialny-delete-dialog.component';
import { EfektMinisterialnyService } from 'app/entities/efekt-ministerialny/efekt-ministerialny.service';

describe('Component Tests', () => {
  describe('EfektMinisterialny Management Delete Component', () => {
    let comp: EfektMinisterialnyDeleteDialogComponent;
    let fixture: ComponentFixture<EfektMinisterialnyDeleteDialogComponent>;
    let service: EfektMinisterialnyService;
    let mockEventManager: MockEventManager;
    let mockActiveModal: MockActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [AppTestModule],
        declarations: [EfektMinisterialnyDeleteDialogComponent]
      })
        .overrideTemplate(EfektMinisterialnyDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(EfektMinisterialnyDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(EfektMinisterialnyService);
      mockEventManager = TestBed.get(JhiEventManager);
      mockActiveModal = TestBed.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.closeSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
      it('Should not call delete service on clear', () => {
        // GIVEN
        spyOn(service, 'delete');

        // WHEN
        comp.clear();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
      });
    });
  });
});
