import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';

@Component({
  selector: 'jhi-efekt-ministerialny-detail',
  templateUrl: './efekt-ministerialny-detail.component.html'
})
export class EfektMinisterialnyDetailComponent implements OnInit {
  efektMinisterialny: IEfektMinisterialny | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ efektMinisterialny }) => {
      this.efektMinisterialny = efektMinisterialny;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
