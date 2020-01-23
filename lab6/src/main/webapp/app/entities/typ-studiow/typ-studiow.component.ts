import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { ITypStudiow } from 'app/shared/model/typ-studiow.model';
import { TypStudiowService } from './typ-studiow.service';
import { TypStudiowDeleteDialogComponent } from './typ-studiow-delete-dialog.component';

@Component({
  selector: 'jhi-typ-studiow',
  templateUrl: './typ-studiow.component.html'
})
export class TypStudiowComponent implements OnInit, OnDestroy {
  typStudiows?: ITypStudiow[];
  eventSubscriber?: Subscription;

  constructor(protected typStudiowService: TypStudiowService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.typStudiowService.query().subscribe((res: HttpResponse<ITypStudiow[]>) => {
      this.typStudiows = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInTypStudiows();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: ITypStudiow): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInTypStudiows(): void {
    this.eventSubscriber = this.eventManager.subscribe('typStudiowListModification', () => this.loadAll());
  }

  delete(typStudiow: ITypStudiow): void {
    const modalRef = this.modalService.open(TypStudiowDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.typStudiow = typStudiow;
  }
}
