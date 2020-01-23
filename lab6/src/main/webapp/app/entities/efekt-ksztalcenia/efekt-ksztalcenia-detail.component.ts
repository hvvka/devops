import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';

@Component({
  selector: 'jhi-efekt-ksztalcenia-detail',
  templateUrl: './efekt-ksztalcenia-detail.component.html'
})
export class EfektKsztalceniaDetailComponent implements OnInit {
  efektKsztalcenia: IEfektKsztalcenia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ efektKsztalcenia }) => {
      this.efektKsztalcenia = efektKsztalcenia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
