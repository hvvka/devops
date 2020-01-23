import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IZajecie } from 'app/shared/model/zajecie.model';

@Component({
  selector: 'jhi-zajecie-detail',
  templateUrl: './zajecie-detail.component.html'
})
export class ZajecieDetailComponent implements OnInit {
  zajecie: IZajecie | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zajecie }) => {
      this.zajecie = zajecie;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
