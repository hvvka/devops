import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';

@Component({
  selector: 'jhi-opiekun-przedmiotu-detail',
  templateUrl: './opiekun-przedmiotu-detail.component.html'
})
export class OpiekunPrzedmiotuDetailComponent implements OnInit {
  opiekunPrzedmiotu: IOpiekunPrzedmiotu | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opiekunPrzedmiotu }) => {
      this.opiekunPrzedmiotu = opiekunPrzedmiotu;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
