import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IOpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';
import { OpiekunPrzedmiotuService } from './opiekun-przedmiotu.service';

@Component({
  templateUrl: './opiekun-przedmiotu-delete-dialog.component.html'
})
export class OpiekunPrzedmiotuDeleteDialogComponent {
  opiekunPrzedmiotu?: IOpiekunPrzedmiotu;

  constructor(
    protected opiekunPrzedmiotuService: OpiekunPrzedmiotuService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.opiekunPrzedmiotuService.delete(id).subscribe(() => {
      this.eventManager.broadcast('opiekunPrzedmiotuListModification');
      this.activeModal.close();
    });
  }
}
