import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IZajecie, Zajecie } from 'app/shared/model/zajecie.model';
import { ZajecieService } from './zajecie.service';
import { ZajecieComponent } from './zajecie.component';
import { ZajecieDetailComponent } from './zajecie-detail.component';
import { ZajecieUpdateComponent } from './zajecie-update.component';

@Injectable({ providedIn: 'root' })
export class ZajecieResolve implements Resolve<IZajecie> {
  constructor(private service: ZajecieService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IZajecie> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((zajecie: HttpResponse<Zajecie>) => {
          if (zajecie.body) {
            return of(zajecie.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Zajecie());
  }
}

export const zajecieRoute: Routes = [
  {
    path: '',
    component: ZajecieComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.zajecie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ZajecieDetailComponent,
    resolve: {
      zajecie: ZajecieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.zajecie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ZajecieUpdateComponent,
    resolve: {
      zajecie: ZajecieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.zajecie.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ZajecieUpdateComponent,
    resolve: {
      zajecie: ZajecieResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.zajecie.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
