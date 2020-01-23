import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IZajecie } from 'app/shared/model/zajecie.model';
import { ZajecieService } from './zajecie.service';
import { ZajecieDeleteDialogComponent } from './zajecie-delete-dialog.component';

@Component({
  selector: 'jhi-zajecie',
  templateUrl: './zajecie.component.html'
})
export class ZajecieComponent implements OnInit, OnDestroy {
  zajecies?: IZajecie[];
  eventSubscriber?: Subscription;

  constructor(protected zajecieService: ZajecieService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.zajecieService.query().subscribe((res: HttpResponse<IZajecie[]>) => {
      this.zajecies = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInZajecies();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IZajecie): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInZajecies(): void {
    this.eventSubscriber = this.eventManager.subscribe('zajecieListModification', () => this.loadAll());
  }

  delete(zajecie: IZajecie): void {
    const modalRef = this.modalService.open(ZajecieDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.zajecie = zajecie;
  }
}
