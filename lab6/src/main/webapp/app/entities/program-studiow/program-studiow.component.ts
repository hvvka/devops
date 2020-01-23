import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProgramStudiowService } from './program-studiow.service';
import { ProgramStudiowDeleteDialogComponent } from './program-studiow-delete-dialog.component';

@Component({
  selector: 'jhi-program-studiow',
  templateUrl: './program-studiow.component.html'
})
export class ProgramStudiowComponent implements OnInit, OnDestroy {
  programStudiows?: IProgramStudiow[];
  eventSubscriber?: Subscription;

  constructor(
    protected programStudiowService: ProgramStudiowService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.programStudiowService.query().subscribe((res: HttpResponse<IProgramStudiow[]>) => {
      this.programStudiows = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInProgramStudiows();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IProgramStudiow): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInProgramStudiows(): void {
    this.eventSubscriber = this.eventManager.subscribe('programStudiowListModification', () => this.loadAll());
  }

  delete(programStudiow: IProgramStudiow): void {
    const modalRef = this.modalService.open(ProgramStudiowDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.programStudiow = programStudiow;
  }
}
