import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IPrzedmiot } from 'app/shared/model/przedmiot.model';
import { PrzedmiotService } from './przedmiot.service';
import { PrzedmiotDeleteDialogComponent } from './przedmiot-delete-dialog.component';

@Component({
  selector: 'jhi-przedmiot',
  templateUrl: './przedmiot.component.html'
})
export class PrzedmiotComponent implements OnInit, OnDestroy {
  przedmiots?: IPrzedmiot[];
  eventSubscriber?: Subscription;

  constructor(protected przedmiotService: PrzedmiotService, protected eventManager: JhiEventManager, protected modalService: NgbModal) {}

  loadAll(): void {
    this.przedmiotService.query().subscribe((res: HttpResponse<IPrzedmiot[]>) => {
      this.przedmiots = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInPrzedmiots();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IPrzedmiot): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInPrzedmiots(): void {
    this.eventSubscriber = this.eventManager.subscribe('przedmiotListModification', () => this.loadAll());
  }

  delete(przedmiot: IPrzedmiot): void {
    const modalRef = this.modalService.open(PrzedmiotDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.przedmiot = przedmiot;
  }

  downloadPdf(przedmiot: IPrzedmiot): void {
    const id: number = przedmiot.id || -1;
    const nazwa: string = przedmiot.nazwa || 'default';

    this.przedmiotService.downloadPdf(id).subscribe(response => {
      const newBlob = new Blob([response], { type: 'application/pdf' });

      if (window.navigator && window.navigator.msSaveOrOpenBlob) {
        window.navigator.msSaveOrOpenBlob(newBlob);
        return;
      }
      const data = window.URL.createObjectURL(newBlob);

      const link = document.createElement('a');
      link.href = data;
      link.download = `${nazwa}_karta_przedmiotu.pdf`;

      link.dispatchEvent(new MouseEvent('click', { bubbles: true, cancelable: true, view: window }));

      setTimeout(function(): void {
        window.URL.revokeObjectURL(data);
        link.remove();
      }, 100);
    });
  }
}
