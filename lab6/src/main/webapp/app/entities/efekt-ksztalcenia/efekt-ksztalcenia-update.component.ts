import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { EfektKsztalcenia, IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';
import { EfektKsztalceniaService } from './efekt-ksztalcenia.service';
import { IProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProgramStudiowService } from 'app/entities/program-studiow/program-studiow.service';
import { IPrzedmiot } from 'app/shared/model/przedmiot.model';
import { PrzedmiotService } from 'app/entities/przedmiot/przedmiot.service';
import { IEfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';
import { EfektMinisterialnyService } from 'app/entities/efekt-ministerialny/efekt-ministerialny.service';

type SelectableEntity = IProgramStudiow | IPrzedmiot | IEfektMinisterialny;

type SelectableManyToManyEntity = IPrzedmiot | IEfektMinisterialny;

@Component({
  selector: 'jhi-efekt-ksztalcenia-update',
  templateUrl: './efekt-ksztalcenia-update.component.html'
})
export class EfektKsztalceniaUpdateComponent implements OnInit {
  isSaving = false;

  programstudiows: IProgramStudiow[] = [];

  przedmiots: IPrzedmiot[] = [];

  efektministerialnies: IEfektMinisterialny[] = [];

  editForm = this.fb.group({
    id: [],
    kodEfektu: [null, [Validators.required]],
    opis: [null, [Validators.required]],
    programStudiow: [],
    przedmiots: [],
    efektMinisterialnies: []
  });

  constructor(
    protected efektKsztalceniaService: EfektKsztalceniaService,
    protected programStudiowService: ProgramStudiowService,
    protected przedmiotService: PrzedmiotService,
    protected efektMinisterialnyService: EfektMinisterialnyService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ efektKsztalcenia }) => {
      this.updateForm(efektKsztalcenia);

      this.programStudiowService
        .query()
        .pipe(
          map((res: HttpResponse<IProgramStudiow[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IProgramStudiow[]) => (this.programstudiows = resBody));

      this.przedmiotService
        .query()
        .pipe(
          map((res: HttpResponse<IPrzedmiot[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPrzedmiot[]) => (this.przedmiots = resBody));

      this.efektMinisterialnyService
        .query()
        .pipe(
          map((res: HttpResponse<IEfektMinisterialny[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IEfektMinisterialny[]) => (this.efektministerialnies = resBody));
    });
  }

  updateForm(efektKsztalcenia: IEfektKsztalcenia): void {
    this.editForm.patchValue({
      id: efektKsztalcenia.id,
      kodEfektu: efektKsztalcenia.kodEfektu,
      opis: efektKsztalcenia.opis,
      programStudiow: efektKsztalcenia.programStudiow,
      przedmiots: efektKsztalcenia.przedmiots,
      efektMinisterialnies: efektKsztalcenia.efektMinisterialnies
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const efektKsztalcenia = this.createFromForm();
    if (efektKsztalcenia.id !== undefined) {
      this.subscribeToSaveResponse(this.efektKsztalceniaService.update(efektKsztalcenia));
    } else {
      this.subscribeToSaveResponse(this.efektKsztalceniaService.create(efektKsztalcenia));
    }
  }

  private createFromForm(): IEfektKsztalcenia {
    return {
      ...new EfektKsztalcenia(),
      id: this.editForm.get(['id'])!.value,
      kodEfektu: this.editForm.get(['kodEfektu'])!.value,
      opis: this.editForm.get(['opis'])!.value,
      programStudiow: this.editForm.get(['programStudiow'])!.value,
      przedmiots: this.editForm.get(['przedmiots'])!.value,
      efektMinisterialnies: this.editForm.get(['efektMinisterialnies'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEfektKsztalcenia>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }

  getSelected(selectedVals: SelectableManyToManyEntity[], option: SelectableManyToManyEntity): SelectableManyToManyEntity {
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
