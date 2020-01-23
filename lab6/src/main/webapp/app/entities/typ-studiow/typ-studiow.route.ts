import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITypStudiow, TypStudiow } from 'app/shared/model/typ-studiow.model';
import { TypStudiowService } from './typ-studiow.service';
import { TypStudiowComponent } from './typ-studiow.component';
import { TypStudiowDetailComponent } from './typ-studiow-detail.component';
import { TypStudiowUpdateComponent } from './typ-studiow-update.component';

@Injectable({ providedIn: 'root' })
export class TypStudiowResolve implements Resolve<ITypStudiow> {
  constructor(private service: TypStudiowService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITypStudiow> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((typStudiow: HttpResponse<TypStudiow>) => {
          if (typStudiow.body) {
            return of(typStudiow.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TypStudiow());
  }
}

export const typStudiowRoute: Routes = [
  {
    path: '',
    component: TypStudiowComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.typStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TypStudiowDetailComponent,
    resolve: {
      typStudiow: TypStudiowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.typStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TypStudiowUpdateComponent,
    resolve: {
      typStudiow: TypStudiowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.typStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TypStudiowUpdateComponent,
    resolve: {
      typStudiow: TypStudiowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.typStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
