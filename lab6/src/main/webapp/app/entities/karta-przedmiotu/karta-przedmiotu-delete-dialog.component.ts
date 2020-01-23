import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IKartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';
import { KartaPrzedmiotuService } from './karta-przedmiotu.service';

@Component({
  templateUrl: './karta-przedmiotu-delete-dialog.component.html'
})
export class KartaPrzedmiotuDeleteDialogComponent {
  kartaPrzedmiotu?: IKartaPrzedmiotu;

  constructor(
    protected kartaPrzedmiotuService: KartaPrzedmiotuService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.kartaPrzedmiotuService.delete(id).subscribe(() => {
      this.eventManager.broadcast('kartaPrzedmiotuListModification');
      this.activeModal.close();
    });
  }
}
