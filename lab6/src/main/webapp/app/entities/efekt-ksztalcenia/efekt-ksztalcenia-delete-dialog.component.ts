import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';
import { EfektKsztalceniaService } from './efekt-ksztalcenia.service';

@Component({
  templateUrl: './efekt-ksztalcenia-delete-dialog.component.html'
})
export class EfektKsztalceniaDeleteDialogComponent {
  efektKsztalcenia?: IEfektKsztalcenia;

  constructor(
    protected efektKsztalceniaService: EfektKsztalceniaService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.efektKsztalceniaService.delete(id).subscribe(() => {
      this.eventManager.broadcast('efektKsztalceniaListModification');
      this.activeModal.close();
    });
  }
}
