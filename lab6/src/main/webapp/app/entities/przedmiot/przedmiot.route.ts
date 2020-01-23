import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IPrzedmiot, Przedmiot } from 'app/shared/model/przedmiot.model';
import { PrzedmiotService } from './przedmiot.service';
import { PrzedmiotComponent } from './przedmiot.component';
import { PrzedmiotDetailComponent } from './przedmiot-detail.component';
import { PrzedmiotUpdateComponent } from './przedmiot-update.component';

@Injectable({ providedIn: 'root' })
export class PrzedmiotResolve implements Resolve<IPrzedmiot> {
  constructor(private service: PrzedmiotService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IPrzedmiot> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((przedmiot: HttpResponse<Przedmiot>) => {
          if (przedmiot.body) {
            return of(przedmiot.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Przedmiot());
  }
}

export const przedmiotRoute: Routes = [
  {
    path: '',
    component: PrzedmiotComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.przedmiot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: PrzedmiotDetailComponent,
    resolve: {
      przedmiot: PrzedmiotResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.przedmiot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: PrzedmiotUpdateComponent,
    resolve: {
      przedmiot: PrzedmiotResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.przedmiot.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: PrzedmiotUpdateComponent,
    resolve: {
      przedmiot: PrzedmiotResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.przedmiot.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
