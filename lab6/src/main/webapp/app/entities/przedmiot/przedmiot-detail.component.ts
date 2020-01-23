import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPrzedmiot } from 'app/shared/model/przedmiot.model';

@Component({
  selector: 'jhi-przedmiot-detail',
  templateUrl: './przedmiot-detail.component.html'
})
export class PrzedmiotDetailComponent implements OnInit {
  przedmiot: IPrzedmiot | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ przedmiot }) => {
      this.przedmiot = przedmiot;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
