import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IKartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';

@Component({
  selector: 'jhi-karta-przedmiotu-detail',
  templateUrl: './karta-przedmiotu-detail.component.html'
})
export class KartaPrzedmiotuDetailComponent implements OnInit {
  kartaPrzedmiotu: IKartaPrzedmiotu | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kartaPrzedmiotu }) => {
      this.kartaPrzedmiotu = kartaPrzedmiotu;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
