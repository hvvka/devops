import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';
import { EfektMinisterialnyService } from './efekt-ministerialny.service';

@Component({
  templateUrl: './efekt-ministerialny-delete-dialog.component.html'
})
export class EfektMinisterialnyDeleteDialogComponent {
  efektMinisterialny?: IEfektMinisterialny;

  constructor(
    protected efektMinisterialnyService: EfektMinisterialnyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.efektMinisterialnyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('efektMinisterialnyListModification');
      this.activeModal.close();
    });
  }
}
