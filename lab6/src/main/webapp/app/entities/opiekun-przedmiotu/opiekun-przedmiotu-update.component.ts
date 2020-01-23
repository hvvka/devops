import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IOpiekunPrzedmiotu, OpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';
import { OpiekunPrzedmiotuService } from './opiekun-przedmiotu.service';

@Component({
  selector: 'jhi-opiekun-przedmiotu-update',
  templateUrl: './opiekun-przedmiotu-update.component.html'
})
export class OpiekunPrzedmiotuUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: []
  });

  constructor(
    protected opiekunPrzedmiotuService: OpiekunPrzedmiotuService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ opiekunPrzedmiotu }) => {
      this.updateForm(opiekunPrzedmiotu);
    });
  }

  updateForm(opiekunPrzedmiotu: IOpiekunPrzedmiotu): void {
    this.editForm.patchValue({
      id: opiekunPrzedmiotu.id
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const opiekunPrzedmiotu = this.createFromForm();
    if (opiekunPrzedmiotu.id !== undefined) {
      this.subscribeToSaveResponse(this.opiekunPrzedmiotuService.update(opiekunPrzedmiotu));
    } else {
      this.subscribeToSaveResponse(this.opiekunPrzedmiotuService.create(opiekunPrzedmiotu));
    }
  }

  private createFromForm(): IOpiekunPrzedmiotu {
    return {
      ...new OpiekunPrzedmiotu(),
      id: this.editForm.get(['id'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IOpiekunPrzedmiotu>>): void {
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
