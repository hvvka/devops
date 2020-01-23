import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IOpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';
import { OpiekunPrzedmiotuService } from './opiekun-przedmiotu.service';
import { OpiekunPrzedmiotuDeleteDialogComponent } from './opiekun-przedmiotu-delete-dialog.component';

@Component({
  selector: 'jhi-opiekun-przedmiotu',
  templateUrl: './opiekun-przedmiotu.component.html'
})
export class OpiekunPrzedmiotuComponent implements OnInit, OnDestroy {
  opiekunPrzedmiotus?: IOpiekunPrzedmiotu[];
  eventSubscriber?: Subscription;

  constructor(
    protected opiekunPrzedmiotuService: OpiekunPrzedmiotuService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.opiekunPrzedmiotuService.query().subscribe((res: HttpResponse<IOpiekunPrzedmiotu[]>) => {
      this.opiekunPrzedmiotus = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInOpiekunPrzedmiotus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IOpiekunPrzedmiotu): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInOpiekunPrzedmiotus(): void {
    this.eventSubscriber = this.eventManager.subscribe('opiekunPrzedmiotuListModification', () => this.loadAll());
  }

  delete(opiekunPrzedmiotu: IOpiekunPrzedmiotu): void {
    const modalRef = this.modalService.open(OpiekunPrzedmiotuDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.opiekunPrzedmiotu = opiekunPrzedmiotu;
  }
}
