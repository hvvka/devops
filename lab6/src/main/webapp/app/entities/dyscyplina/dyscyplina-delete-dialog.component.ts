import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDyscyplina } from 'app/shared/model/dyscyplina.model';
import { DyscyplinaService } from './dyscyplina.service';

@Component({
  templateUrl: './dyscyplina-delete-dialog.component.html'
})
export class DyscyplinaDeleteDialogComponent {
  dyscyplina?: IDyscyplina;

  constructor(
    protected dyscyplinaService: DyscyplinaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.dyscyplinaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('dyscyplinaListModification');
      this.activeModal.close();
    });
  }
}
