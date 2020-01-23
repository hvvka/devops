import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ITypStudiow, TypStudiow } from 'app/shared/model/typ-studiow.model';
import { TypStudiowService } from './typ-studiow.service';

@Component({
  selector: 'jhi-typ-studiow-update',
  templateUrl: './typ-studiow-update.component.html'
})
export class TypStudiowUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nazwa: [null, [Validators.required]],
    stopienStudiow: []
  });

  constructor(protected typStudiowService: TypStudiowService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ typStudiow }) => {
      this.updateForm(typStudiow);
    });
  }

  updateForm(typStudiow: ITypStudiow): void {
    this.editForm.patchValue({
      id: typStudiow.id,
      nazwa: typStudiow.nazwa,
      stopienStudiow: typStudiow.stopienStudiow
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const typStudiow = this.createFromForm();
    if (typStudiow.id !== undefined) {
      this.subscribeToSaveResponse(this.typStudiowService.update(typStudiow));
    } else {
      this.subscribeToSaveResponse(this.typStudiowService.create(typStudiow));
    }
  }

  private createFromForm(): ITypStudiow {
    return {
      ...new TypStudiow(),
      id: this.editForm.get(['id'])!.value,
      nazwa: this.editForm.get(['nazwa'])!.value,
      stopienStudiow: this.editForm.get(['stopienStudiow'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypStudiow>>): void {
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
