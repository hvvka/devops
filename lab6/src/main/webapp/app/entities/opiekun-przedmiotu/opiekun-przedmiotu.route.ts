import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IOpiekunPrzedmiotu, OpiekunPrzedmiotu } from 'app/shared/model/opiekun-przedmiotu.model';
import { OpiekunPrzedmiotuService } from './opiekun-przedmiotu.service';
import { OpiekunPrzedmiotuComponent } from './opiekun-przedmiotu.component';
import { OpiekunPrzedmiotuDetailComponent } from './opiekun-przedmiotu-detail.component';
import { OpiekunPrzedmiotuUpdateComponent } from './opiekun-przedmiotu-update.component';

@Injectable({ providedIn: 'root' })
export class OpiekunPrzedmiotuResolve implements Resolve<IOpiekunPrzedmiotu> {
  constructor(private service: OpiekunPrzedmiotuService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IOpiekunPrzedmiotu> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((opiekunPrzedmiotu: HttpResponse<OpiekunPrzedmiotu>) => {
          if (opiekunPrzedmiotu.body) {
            return of(opiekunPrzedmiotu.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new OpiekunPrzedmiotu());
  }
}

export const opiekunPrzedmiotuRoute: Routes = [
  {
    path: '',
    component: OpiekunPrzedmiotuComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.opiekunPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: OpiekunPrzedmiotuDetailComponent,
    resolve: {
      opiekunPrzedmiotu: OpiekunPrzedmiotuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.opiekunPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: OpiekunPrzedmiotuUpdateComponent,
    resolve: {
      opiekunPrzedmiotu: OpiekunPrzedmiotuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.opiekunPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: OpiekunPrzedmiotuUpdateComponent,
    resolve: {
      opiekunPrzedmiotu: OpiekunPrzedmiotuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.opiekunPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
