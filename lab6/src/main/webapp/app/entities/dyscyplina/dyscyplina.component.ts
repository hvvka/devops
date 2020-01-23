import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDyscyplina } from 'app/shared/model/dyscyplina.model';
import { DyscyplinaService } from './dyscyplina.service';
import { DyscyplinaDeleteDialogComponent } from './dyscyplina-delete-dialog.component';

@Component({
  selector: 'jhi-dyscyplina',
  templateUrl: './dyscyplina.component.html'
})
export class DyscyplinaComponent implements OnInit, OnDestroy {
  dyscyplinas?: IDyscyplina[];
  eventSubscriber?: Subscription;

  constructor(protected dyscyplinaService: DyscyplinaService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.dyscyplinaService.query().subscribe((res: HttpResponse<IDyscyplina[]>) => {
      this.dyscyplinas = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInDyscyplinas();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IDyscyplina): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInDyscyplinas(): void {
    this.eventSubscriber = this.eventManager.subscribe('dyscyplinaListModification', () => this.loadAll());
  }

  delete(dyscyplina: IDyscyplina): void {
    const modalRef = this.modalService.open(DyscyplinaDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.dyscyplina = dyscyplina;
  }
}
