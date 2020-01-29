import { Component, OnDestroy, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';
import { MacierzSladowaniaService } from './macierz-sladowania.service';

@Component({
  selector: 'jhi-efekt-ksztalcenia',
  templateUrl: './macierz-sladowania.component.html'
})
export class MacierzSladowaniaComponent implements OnInit, OnDestroy {
  efektKsztalcenias?: IEfektKsztalcenia[];
  eventSubscriber?: Subscription;

  constructor(
    protected efektKsztalceniaService: MacierzSladowaniaService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.efektKsztalceniaService.query().subscribe((res: HttpResponse<IEfektKsztalcenia[]>) => {
      this.efektKsztalcenias = res.body ? res.body : [];
    });
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInEfektKsztalcenias();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IEfektKsztalcenia): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInEfektKsztalcenias(): void {
    this.eventSubscriber = this.eventManager.subscribe('efektKsztalceniaListModification', () => this.loadAll());
  }
}
