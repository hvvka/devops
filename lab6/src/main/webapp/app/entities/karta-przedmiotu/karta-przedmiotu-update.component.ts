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
    kodPrzedmiotu: [null, [Validators.required]],
    nazwa: [null, [Validators.required]],
    nazwaAng: [null, [Validators.required]],
    rodzajPrzedmiotu: [null, [Validators.required]]
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
      kodPrzedmiotu: kartaPrzedmiotu.kodPrzedmiotu,
      nazwa: kartaPrzedmiotu.nazwa,
      nazwaAng: kartaPrzedmiotu.nazwaAng,
      rodzajPrzedmiotu: kartaPrzedmiotu.rodzajPrzedmiotu
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
      kodPrzedmiotu: this.editForm.get(['kodPrzedmiotu'])!.value,
      nazwa: this.editForm.get(['nazwa'])!.value,
      nazwaAng: this.editForm.get(['nazwaAng'])!.value,
      rodzajPrzedmiotu: this.editForm.get(['rodzajPrzedmiotu'])!.value
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
