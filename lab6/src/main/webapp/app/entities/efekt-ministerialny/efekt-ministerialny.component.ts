import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';
import { EfektMinisterialnyService } from './efekt-ministerialny.service';
import { EfektMinisterialnyDeleteDialogComponent } from './efekt-ministerialny-delete-dialog.component';

@Component({
  selector: 'jhi-efekt-ministerialny',
  templateUrl: './efekt-ministerialny.component.html'
})
export class EfektMinisterialnyComponent implements OnInit, OnDestroy {
  efektMinisterialnies?: IEfektMinisterialny[];
  eventSubscriber?: Subscription;

  constructor(
    protected efektMinisterialnyService: EfektMinisterialnyService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.efektMinisterialnyService.query().subscribe((res: HttpResponse<IEfektMinisterialny[]>) => {
      this.efektMinisterialnies = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEfektMinisterialnies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEfektMinisterialny): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEfektMinisterialnies(): void {
    this.eventSubscriber = this.eventManager.subscribe('efektMinisterialnyListModification', () => this.loadAll());
  }

  delete(efektMinisterialny: IEfektMinisterialny): void {
    const modalRef = this.modalService.open(EfektMinisterialnyDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.efektMinisterialny = efektMinisterialny;
  }
}
