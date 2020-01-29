import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IPrzedmiot, Przedmiot } from 'app/shared/model/przedmiot.model';
import { PrzedmiotService } from './przedmiot.service';
import { IKartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';
import { KartaPrzedmiotuService } from 'app/entities/karta-przedmiotu/karta-przedmiotu.service';
import { IOpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';
import { OpiekunPrzedmiotuService } from 'app/entities/opiekun-przedmiotu/opiekun-przedmiotu.service';
import { IProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProgramStudiowService } from 'app/entities/program-studiow/program-studiow.service';

type SelectableEntity = IKartaPrzedmiotu | IOpiekunPrzedmiotu | IProgramStudiow;

@Component({
  selector: 'jhi-przedmiot-update',
  templateUrl: './przedmiot-update.component.html'
})
export class PrzedmiotUpdateComponent implements OnInit {
  isSaving = false;

  kartaprzedmiotus: IKartaPrzedmiotu[] = [];

  opiekunprzedmiotus: IOpiekunPrzedmiotu[] = [];

  programstudiows: IProgramStudiow[] = [];

  editForm = this.fb.group({
    id: [],
    nrSemestru: [null, [Validators.required]],
    nazwa: [],
    kartaPrzedmiotu: [],
    opiekunPrzedmiotu: [],
    programStudiow: []
  });

  constructor(
    protected przedmiotService: PrzedmiotService,
    protected kartaPrzedmiotuService: KartaPrzedmiotuService,
    protected opiekunPrzedmiotuService: OpiekunPrzedmiotuService,
    protected programStudiowService: ProgramStudiowService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ przedmiot }) => {
      this.updateForm(przedmiot);

      this.kartaPrzedmiotuService
        .query()
        .pipe(
          map((res: HttpResponse<IKartaPrzedmiotu[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IKartaPrzedmiotu[]) => (this.kartaprzedmiotus = resBody));

      this.opiekunPrzedmiotuService
        .query()
        .pipe(
          map((res: HttpResponse<IOpiekunPrzedmiotu[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IOpiekunPrzedmiotu[]) => (this.opiekunprzedmiotus = resBody));

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

  updateForm(przedmiot: IPrzedmiot): void {
    this.editForm.patchValue({
      id: przedmiot.id,
      nrSemestru: przedmiot.nrSemestru,
      nazwa: przedmiot.nazwa,
      kartaPrzedmiotu: przedmiot.kartaPrzedmiotu,
      opiekunPrzedmiotu: przedmiot.opiekunPrzedmiotu,
      programStudiow: przedmiot.programStudiow
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const przedmiot = this.createFromForm();
    if (przedmiot.id !== undefined) {
      this.subscribeToSaveResponse(this.przedmiotService.update(przedmiot));
    } else {
      this.subscribeToSaveResponse(this.przedmiotService.create(przedmiot));
    }
  }

  private createFromForm(): IPrzedmiot {
    return {
      ...new Przedmiot(),
      id: this.editForm.get(['id'])!.value,
      nrSemestru: this.editForm.get(['nrSemestru'])!.value,
      nazwa: this.editForm.get(['nazwa'])!.value,
      kartaPrzedmiotu: this.editForm.get(['kartaPrzedmiotu'])!.value,
      opiekunPrzedmiotu: this.editForm.get(['opiekunPrzedmiotu'])!.value,
      programStudiow: this.editForm.get(['programStudiow'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPrzedmiot>>): void {
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
}
