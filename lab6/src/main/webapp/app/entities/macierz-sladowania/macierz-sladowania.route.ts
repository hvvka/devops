import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Resolve, Router, Routes } from '@angular/router';
import { EMPTY, Observable, of } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { EfektKsztalcenia, IEfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';
import { MacierzSladowaniaService } from './macierz-sladowania.service';
import { MacierzSladowaniaComponent } from './macierz-sladowania.component';

@Injectable({ providedIn: 'root' })
export class EfektKsztalceniaResolve implements Resolve<IEfektKsztalcenia> {
  constructor(private service: MacierzSladowaniaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEfektKsztalcenia> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((efektKsztalcenia: HttpResponse<EfektKsztalcenia>) => {
          if (efektKsztalcenia.body) {
            return of(efektKsztalcenia.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EfektKsztalcenia());
  }
}

export const macierzSladowaniaRoute: Routes = [
  {
    path: '',
    component: MacierzSladowaniaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektKsztalcenia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
