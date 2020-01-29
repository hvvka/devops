import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { EfektMinisterialny, IEfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';
import { EfektMinisterialnyService } from './efekt-ministerialny.service';

@Component({
  selector: 'jhi-efekt-ministerialny-update',
  templateUrl: './efekt-ministerialny-update.component.html'
})
export class EfektMinisterialnyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    kodEfektu: [null, [Validators.required]],
    poziomEfektu: [null, [Validators.required]],
    typEfektuMinisterialnego: [null, [Validators.required]]
  });

  constructor(
    protected efektMinisterialnyService: EfektMinisterialnyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ efektMinisterialny }) => {
      this.updateForm(efektMinisterialny);
    });
  }

  updateForm(efektMinisterialny: IEfektMinisterialny): void {
    this.editForm.patchValue({
      id: efektMinisterialny.id,
      kodEfektu: efektMinisterialny.kodEfektu,
      poziomEfektu: efektMinisterialny.poziomEfektu,
      typEfektuMinisterialnego: efektMinisterialny.typEfektuMinisterialnego
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const efektMinisterialny = this.createFromForm();
    if (efektMinisterialny.id !== undefined) {
      this.subscribeToSaveResponse(this.efektMinisterialnyService.update(efektMinisterialny));
    } else {
      this.subscribeToSaveResponse(this.efektMinisterialnyService.create(efektMinisterialny));
    }
  }

  private createFromForm(): IEfektMinisterialny {
    return {
      ...new EfektMinisterialny(),
      id: this.editForm.get(['id'])!.value,
      kodEfektu: this.editForm.get(['kodEfektu'])!.value,
      poziomEfektu: this.editForm.get(['poziomEfektu'])!.value,
      typEfektuMinisterialnego: this.editForm.get(['typEfektuMinisterialnego'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEfektMinisterialny>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }
}
