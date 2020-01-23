import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IZajecie } from 'app/shared/model/zajecie.model';
import { ZajecieService } from './zajecie.service';

@Component({
  templateUrl: './zajecie-delete-dialog.component.html'
})
export class ZajecieDeleteDialogComponent {
  zajecie?: IZajecie;

  constructor(protected zajecieService: ZajecieService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.zajecieService.delete(id).subscribe(() => {
      this.eventManager.broadcast('zajecieListModification');
      this.activeModal.close();
    });
  }
}
