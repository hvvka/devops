import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IKartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';
import { KartaPrzedmiotuService } from './karta-przedmiotu.service';
import { KartaPrzedmiotuDeleteDialogComponent } from './karta-przedmiotu-delete-dialog.component';

@Component({
  selector: 'jhi-karta-przedmiotu',
  templateUrl: './karta-przedmiotu.component.html'
})
export class KartaPrzedmiotuComponent implements OnInit, OnDestroy {
  kartaPrzedmiotus?: IKartaPrzedmiotu[];
  eventSubscriber?: Subscription;

  constructor(
    protected kartaPrzedmiotuService: KartaPrzedmiotuService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.kartaPrzedmiotuService.query().subscribe((res: HttpResponse<IKartaPrzedmiotu[]>) => {
      this.kartaPrzedmiotus = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInKartaPrzedmiotus();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IKartaPrzedmiotu): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInKartaPrzedmiotus(): void {
    this.eventSubscriber = this.eventManager.subscribe('kartaPrzedmiotuListModification', () => this.loadAll());
  }

  delete(kartaPrzedmiotu: IKartaPrzedmiotu): void {
    const modalRef = this.modalService.open(KartaPrzedmiotuDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.kartaPrzedmiotu = kartaPrzedmiotu;
  }

  downloadPdf(kartaPrzedmiotu: IKartaPrzedmiotu): void {
    const id: number = kartaPrzedmiotu.id || -1;
    const nazwa: string = kartaPrzedmiotu.nazwa || 'default';

    this.kartaPrzedmiotuService.downloadPdf(id).subscribe(response => {
      const newBlob = new Blob([response], { type: 'application/pdf' });

      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(newBlob);
        return;
      }
      const data = window.URL.createObjectURL(newBlob);

      const link = document.createElement('a');
      link.href = data;
      link.download = `${nazwa}.pdf`;

      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function(): void {
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
    });
  }
}
