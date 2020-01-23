import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEfektKsztalcenia, EfektKsztalcenia } from 'app/shared/model/efekt-ksztalcenia.model';
import { EfektKsztalceniaService } from './efekt-ksztalcenia.service';
import { EfektKsztalceniaComponent } from './efekt-ksztalcenia.component';
import { EfektKsztalceniaDetailComponent } from './efekt-ksztalcenia-detail.component';
import { EfektKsztalceniaUpdateComponent } from './efekt-ksztalcenia-update.component';

@Injectable({ providedIn: 'root' })
export class EfektKsztalceniaResolve implements Resolve<IEfektKsztalcenia> {
  constructor(private service: EfektKsztalceniaService, private router: Router) {}

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

export const efektKsztalceniaRoute: Routes = [
  {
    path: '',
    component: EfektKsztalceniaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektKsztalcenia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EfektKsztalceniaDetailComponent,
    resolve: {
      efektKsztalcenia: EfektKsztalceniaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektKsztalcenia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EfektKsztalceniaUpdateComponent,
    resolve: {
      efektKsztalcenia: EfektKsztalceniaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektKsztalcenia.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EfektKsztalceniaUpdateComponent,
    resolve: {
      efektKsztalcenia: EfektKsztalceniaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektKsztalcenia.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
