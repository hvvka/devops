import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProgramStudiow } from 'app/shared/model/program-studiow.model';

@Component({
  selector: 'jhi-program-studiow-detail',
  templateUrl: './program-studiow-detail.component.html'
})
export class ProgramStudiowDetailComponent implements OnInit {
  programStudiow: IProgramStudiow | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programStudiow }) => {
      this.programStudiow = programStudiow;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
