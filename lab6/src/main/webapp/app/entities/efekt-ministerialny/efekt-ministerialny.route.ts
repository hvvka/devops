import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEfektMinisterialny, EfektMinisterialny } from 'app/shared/model/efekt-ministerialny.model';
import { EfektMinisterialnyService } from './efekt-ministerialny.service';
import { EfektMinisterialnyComponent } from './efekt-ministerialny.component';
import { EfektMinisterialnyDetailComponent } from './efekt-ministerialny-detail.component';
import { EfektMinisterialnyUpdateComponent } from './efekt-ministerialny-update.component';

@Injectable({ providedIn: 'root' })
export class EfektMinisterialnyResolve implements Resolve<IEfektMinisterialny> {
  constructor(private service: EfektMinisterialnyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEfektMinisterialny> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((efektMinisterialny: HttpResponse<EfektMinisterialny>) => {
          if (efektMinisterialny.body) {
            return of(efektMinisterialny.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EfektMinisterialny());
  }
}

export const efektMinisterialnyRoute: Routes = [
  {
    path: '',
    component: EfektMinisterialnyComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektMinisterialny.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EfektMinisterialnyDetailComponent,
    resolve: {
      efektMinisterialny: EfektMinisterialnyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektMinisterialny.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EfektMinisterialnyUpdateComponent,
    resolve: {
      efektMinisterialny: EfektMinisterialnyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektMinisterialny.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EfektMinisterialnyUpdateComponent,
    resolve: {
      efektMinisterialny: EfektMinisterialnyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.efektMinisterialny.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
