import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IKartaPrzedmiotu, KartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';
import { KartaPrzedmiotuService } from './karta-przedmiotu.service';

@Component({
  selector: 'jhi-karta-przedmiotu-update',
  templateUrl: './karta-przedmiotu-update.component.html'
})
export class KartaPrzedmiotuUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    nazwa: [null, [Validators.required]]
  });

  constructor(
    protected kartaPrzedmiotuService: KartaPrzedmiotuService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ kartaPrzedmiotu }) => {
      this.updateForm(kartaPrzedmiotu);
    });
  }

  updateForm(kartaPrzedmiotu: IKartaPrzedmiotu): void {
    this.editForm.patchValue({
      id: kartaPrzedmiotu.id,
      nazwa: kartaPrzedmiotu.nazwa
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const kartaPrzedmiotu = this.createFromForm();
    if (kartaPrzedmiotu.id !== undefined) {
      this.subscribeToSaveResponse(this.kartaPrzedmiotuService.update(kartaPrzedmiotu));
    } else {
      this.subscribeToSaveResponse(this.kartaPrzedmiotuService.create(kartaPrzedmiotu));
    }
  }

  private createFromForm(): IKartaPrzedmiotu {
    return {
      ...new KartaPrzedmiotu(),
      id: this.editForm.get(['id'])!.value,
      nazwa: this.editForm.get(['nazwa'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKartaPrzedmiotu>>): void {
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
