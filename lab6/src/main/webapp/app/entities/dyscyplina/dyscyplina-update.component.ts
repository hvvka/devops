import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IDyscyplina, Dyscyplina } from 'app/shared/model/dyscyplina.model';
import { DyscyplinaService } from './dyscyplina.service';
import { IProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProgramStudiowService } from 'app/entities/program-studiow/program-studiow.service';

@Component({
  selector: 'jhi-dyscyplina-update',
  templateUrl: './dyscyplina-update.component.html'
})
export class DyscyplinaUpdateComponent implements OnInit {
  isSaving = false;

  programstudiows: IProgramStudiow[] = [];

  editForm = this.fb.group({
    id: [],
    nazwa: [],
    czyWiodaca: [null, [Validators.required]],
    progamStudiows: []
  });

  constructor(
    protected dyscyplinaService: DyscyplinaService,
    protected programStudiowService: ProgramStudiowService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ dyscyplina }) => {
      this.updateForm(dyscyplina);

      this.programStudiowService
        .query()
        .pipe(
          map((res: HttpResponse<IProgramStudiow[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProgramStudiow[]) => (this.programstudiows = resBody));
    });
  }

  updateForm(dyscyplina: IDyscyplina): void {
    this.editForm.patchValue({
      id: dyscyplina.id,
      nazwa: dyscyplina.nazwa,
      czyWiodaca: dyscyplina.czyWiodaca,
      progamStudiows: dyscyplina.progamStudiows
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const dyscyplina = this.createFromForm();
    if (dyscyplina.id !== undefined) {
      this.subscribeToSaveResponse(this.dyscyplinaService.update(dyscyplina));
    } else {
      this.subscribeToSaveResponse(this.dyscyplinaService.create(dyscyplina));
    }
  }

  private createFromForm(): IDyscyplina {
    return {
      ...new Dyscyplina(),
      id: this.editForm.get(['id'])!.value,
      nazwa: this.editForm.get(['nazwa'])!.value,
      czyWiodaca: this.editForm.get(['czyWiodaca'])!.value,
      progamStudiows: this.editForm.get(['progamStudiows'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDyscyplina>>): void {
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

  trackById(index: number, item: IProgramStudiow): any {
    return item.id;
  }

  getSelected(selectedVals: IProgramStudiow[], option: IProgramStudiow): IProgramStudiow {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
