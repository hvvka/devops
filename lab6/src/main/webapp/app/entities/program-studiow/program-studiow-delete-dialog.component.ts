import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProgramStudiowService } from './program-studiow.service';

@Component({
  templateUrl: './program-studiow-delete-dialog.component.html'
})
export class ProgramStudiowDeleteDialogComponent {
  programStudiow?: IProgramStudiow;

  constructor(
    protected programStudiowService: ProgramStudiowService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.programStudiowService.delete(id).subscribe(() => {
      this.eventManager.broadcast('programStudiowListModification');
      this.activeModal.close();
    });
  }
}
