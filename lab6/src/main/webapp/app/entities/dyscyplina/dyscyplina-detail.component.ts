import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDyscyplina } from 'app/shared/model/dyscyplina.model';

@Component({
  selector: 'jhi-dyscyplina-detail',
  templateUrl: './dyscyplina-detail.component.html'
})
export class DyscyplinaDetailComponent implements OnInit {
  dyscyplina: IDyscyplina | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dyscyplina }) => {
      this.dyscyplina = dyscyplina;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
