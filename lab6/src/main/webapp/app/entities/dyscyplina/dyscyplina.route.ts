import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IDyscyplina, Dyscyplina } from 'app/shared/model/dyscyplina.model';
import { DyscyplinaService } from './dyscyplina.service';
import { DyscyplinaComponent } from './dyscyplina.component';
import { DyscyplinaDetailComponent } from './dyscyplina-detail.component';
import { DyscyplinaUpdateComponent } from './dyscyplina-update.component';

@Injectable({ providedIn: 'root' })
export class DyscyplinaResolve implements Resolve<IDyscyplina> {
  constructor(private service: DyscyplinaService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IDyscyplina> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((dyscyplina: HttpResponse<Dyscyplina>) => {
          if (dyscyplina.body) {
            return of(dyscyplina.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Dyscyplina());
  }
}

export const dyscyplinaRoute: Routes = [
  {
    path: '',
    component: DyscyplinaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.dyscyplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: DyscyplinaDetailComponent,
    resolve: {
      dyscyplina: DyscyplinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.dyscyplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: DyscyplinaUpdateComponent,
    resolve: {
      dyscyplina: DyscyplinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.dyscyplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: DyscyplinaUpdateComponent,
    resolve: {
      dyscyplina: DyscyplinaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.dyscyplina.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
