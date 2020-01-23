import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IPrzedmiot } from 'app/shared/model/przedmiot.model';
import { PrzedmiotService } from './przedmiot.service';

@Component({
  templateUrl: './przedmiot-delete-dialog.component.html'
})
export class PrzedmiotDeleteDialogComponent {
  przedmiot?: IPrzedmiot;

  constructor(protected przedmiotService: PrzedmiotService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.przedmiotService.delete(id).subscribe(() => {
      this.eventManager.broadcast('przedmiotListModification');
      this.activeModal.close();
    });
  }
}
