import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';

import { IZajecie, Zajecie } from 'app/shared/model/zajecie.model';
import { ZajecieService } from './zajecie.service';
import { IPrzedmiot } from 'app/shared/model/przedmiot.model';
import { PrzedmiotService } from 'app/entities/przedmiot/przedmiot.service';

type SelectableEntity = IZajecie | IPrzedmiot;

@Component({
  selector: 'jhi-zajecie-update',
  templateUrl: './zajecie-update.component.html'
})
export class ZajecieUpdateComponent implements OnInit {
  isSaving = false;

  formawiodacas: IZajecie[] = [];

  zajecies: IZajecie[] = [];

  przedmiots: IPrzedmiot[] = [];

  editForm = this.fb.group({
    id: [],
    forma: [null, [Validators.required]],
    eCTS: [null, [Validators.required]],
    zZU: [null, [Validators.required]],
    cNPS: [null, [Validators.required]],
    modulKsztalcenia: [null, [Validators.required]],
    poziomJezyka: [null, [Validators.required]],
    formaWiodaca: [],
    grupaKursow: [],
    przedmiot: []
  });

  constructor(
    protected zajecieService: ZajecieService,
    protected przedmiotService: PrzedmiotService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ zajecie }) => {
      this.updateForm(zajecie);

      this.zajecieService
        .query({ filter: 'zajecie-is-null' })
        .pipe(
          map((res: HttpResponse<IZajecie[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IZajecie[]) => {
          if (!zajecie.formaWiodaca || !zajecie.formaWiodaca.id) {
            this.formawiodacas = resBody;
          } else {
            this.zajecieService
              .find(zajecie.formaWiodaca.id)
              .pipe(
                map((subRes: HttpResponse<IZajecie>) => {
                  return subRes.body ? [subRes.body].concat(resBody) : resBody;
                })
              )
              .subscribe((concatRes: IZajecie[]) => {
                this.formawiodacas = concatRes;
              });
          }
        });

      this.zajecieService
        .query()
        .pipe(
          map((res: HttpResponse<IZajecie[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IZajecie[]) => (this.zajecies = resBody));

      this.przedmiotService
        .query()
        .pipe(
          map((res: HttpResponse<IPrzedmiot[]>) => {
            return res.body ? res.body : [];
          })
        )
        .subscribe((resBody: IPrzedmiot[]) => (this.przedmiots = resBody));
    });
  }

  updateForm(zajecie: IZajecie): void {
    this.editForm.patchValue({
      id: zajecie.id,
      forma: zajecie.forma,
      eCTS: zajecie.eCTS,
      zZU: zajecie.zZU,
      cNPS: zajecie.cNPS,
      modulKsztalcenia: zajecie.modulKsztalcenia,
      poziomJezyka: zajecie.poziomJezyka,
      formaWiodaca: zajecie.formaWiodaca,
      grupaKursow: zajecie.grupaKursow,
      przedmiot: zajecie.przedmiot
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const zajecie = this.createFromForm();
    if (zajecie.id !== undefined) {
      this.subscribeToSaveResponse(this.zajecieService.update(zajecie));
    } else {
      this.subscribeToSaveResponse(this.zajecieService.create(zajecie));
    }
  }

  private createFromForm(): IZajecie {
    return {
      ...new Zajecie(),
      id: this.editForm.get(['id'])!.value,
      forma: this.editForm.get(['forma'])!.value,
      eCTS: this.editForm.get(['eCTS'])!.value,
      zZU: this.editForm.get(['zZU'])!.value,
      cNPS: this.editForm.get(['cNPS'])!.value,
      modulKsztalcenia: this.editForm.get(['modulKsztalcenia'])!.value,
      poziomJezyka: this.editForm.get(['poziomJezyka'])!.value,
      formaWiodaca: this.editForm.get(['formaWiodaca'])!.value,
      grupaKursow: this.editForm.get(['grupaKursow'])!.value,
      przedmiot: this.editForm.get(['przedmiot'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IZajecie>>): void {
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
