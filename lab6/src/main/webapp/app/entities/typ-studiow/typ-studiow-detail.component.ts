import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITypStudiow } from 'app/shared/model/typ-studiow.model';

@Component({
  selector: 'jhi-typ-studiow-detail',
  templateUrl: './typ-studiow-detail.component.html'
})
export class TypStudiowDetailComponent implements OnInit {
  typStudiow: ITypStudiow | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typStudiow }) => {
      this.typStudiow = typStudiow;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
