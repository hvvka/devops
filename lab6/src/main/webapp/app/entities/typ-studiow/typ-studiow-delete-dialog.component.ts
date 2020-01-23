import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypStudiow } from 'app/shared/model/typ-studiow.model';
import { TypStudiowService } from './typ-studiow.service';

@Component({
  templateUrl: './typ-studiow-delete-dialog.component.html'
})
export class TypStudiowDeleteDialogComponent {
  typStudiow?: ITypStudiow;

  constructor(
    protected typStudiowService: TypStudiowService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.typStudiowService.delete(id).subscribe(() => {
      this.eventManager.broadcast('typStudiowListModification');
      this.activeModal.close();
    });
  }
}
