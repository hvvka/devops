import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IProgramStudiow, ProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProgramStudiowService } from './program-studiow.service';
import { ITypStudiow } from 'app/shared/model/typ-studiow.model';
import { TypStudiowService } from 'app/entities/typ-studiow/typ-studiow.service';

@Component({
  selector: 'jhi-program-studiow-update',
  templateUrl: './program-studiow-update.component.html'
})
export class ProgramStudiowUpdateComponent implements OnInit {
  isSaving = false;

  typstudiows: ITypStudiow[] = [];

  editForm = this.fb.group({
    id: [],
    profilKsztalcenia: [null, [Validators.required]],
    formaStudiow: [null, [Validators.required]],
    kierunek: [null, [Validators.required]],
    wydzial: [null, [Validators.required]],
    jezykProwadzeniaStudiow: [],
    liczbaSemestrow: [null, [Validators.required]],
    cyklKsztalcenia: [null, [Validators.required]],
    typStudiow: []
  });

  constructor(
    protected programStudiowService: ProgramStudiowService,
    protected typStudiowService: TypStudiowService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ programStudiow }) => {
      this.updateForm(programStudiow);

      this.typStudiowService
        .query()
        .pipe(
          map((res: HttpResponse<ITypStudiow[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: ITypStudiow[]) => (this.typstudiows = resBody));
    });
  }

  updateForm(programStudiow: IProgramStudiow): void {
    this.editForm.patchValue({
      id: programStudiow.id,
      profilKsztalcenia: programStudiow.profilKsztalcenia,
      formaStudiow: programStudiow.formaStudiow,
      kierunek: programStudiow.kierunek,
      wydzial: programStudiow.wydzial,
      jezykProwadzeniaStudiow: programStudiow.jezykProwadzeniaStudiow,
      liczbaSemestrow: programStudiow.liczbaSemestrow,
      cyklKsztalcenia: programStudiow.cyklKsztalcenia,
      typStudiow: programStudiow.typStudiow
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const programStudiow = this.createFromForm();
    if (programStudiow.id !== undefined) {
      this.subscribeToSaveResponse(this.programStudiowService.update(programStudiow));
    } else {
      this.subscribeToSaveResponse(this.programStudiowService.create(programStudiow));
    }
  }

  private createFromForm(): IProgramStudiow {
    return {
      ...new ProgramStudiow(),
      id: this.editForm.get(['id'])!.value,
      profilKsztalcenia: this.editForm.get(['profilKsztalcenia'])!.value,
      formaStudiow: this.editForm.get(['formaStudiow'])!.value,
      kierunek: this.editForm.get(['kierunek'])!.value,
      wydzial: this.editForm.get(['wydzial'])!.value,
      jezykProwadzeniaStudiow: this.editForm.get(['jezykProwadzeniaStudiow'])!.value,
      liczbaSemestrow: this.editForm.get(['liczbaSemestrow'])!.value,
      cyklKsztalcenia: this.editForm.get(['cyklKsztalcenia'])!.value,
      typStudiow: this.editForm.get(['typStudiow'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProgramStudiow>>): void {
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

  trackById(index: number, item: ITypStudiow): any {
    return item.id;
  }
}
