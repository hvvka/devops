import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IKartaPrzedmiotu, KartaPrzedmiotu } from 'app/shared/model/karta-przedmiotu.model';
import { KartaPrzedmiotuService } from './karta-przedmiotu.service';
import { KartaPrzedmiotuComponent } from './karta-przedmiotu.component';
import { KartaPrzedmiotuDetailComponent } from './karta-przedmiotu-detail.component';
import { KartaPrzedmiotuUpdateComponent } from './karta-przedmiotu-update.component';

@Injectable({ providedIn: 'root' })
export class KartaPrzedmiotuResolve implements Resolve<IKartaPrzedmiotu> {
  constructor(private service: KartaPrzedmiotuService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IKartaPrzedmiotu> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((kartaPrzedmiotu: HttpResponse<KartaPrzedmiotu>) => {
          if (kartaPrzedmiotu.body) {
            return of(kartaPrzedmiotu.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new KartaPrzedmiotu());
  }
}

export const kartaPrzedmiotuRoute: Routes = [
  {
    path: '',
    component: KartaPrzedmiotuComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.kartaPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: KartaPrzedmiotuDetailComponent,
    resolve: {
      kartaPrzedmiotu: KartaPrzedmiotuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.kartaPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: KartaPrzedmiotuUpdateComponent,
    resolve: {
      kartaPrzedmiotu: KartaPrzedmiotuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.kartaPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: KartaPrzedmiotuUpdateComponent,
    resolve: {
      kartaPrzedmiotu: KartaPrzedmiotuResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.kartaPrzedmiotu.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
