import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IProgramStudiow, ProgramStudiow } from 'app/shared/model/program-studiow.model';
import { ProgramStudiowService } from './program-studiow.service';
import { ProgramStudiowComponent } from './program-studiow.component';
import { ProgramStudiowDetailComponent } from './program-studiow-detail.component';
import { ProgramStudiowUpdateComponent } from './program-studiow-update.component';

@Injectable({ providedIn: 'root' })
export class ProgramStudiowResolve implements Resolve<IProgramStudiow> {
  constructor(private service: ProgramStudiowService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IProgramStudiow> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((programStudiow: HttpResponse<ProgramStudiow>) => {
          if (programStudiow.body) {
            return of(programStudiow.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ProgramStudiow());
  }
}

export const programStudiowRoute: Routes = [
  {
    path: '',
    component: ProgramStudiowComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.programStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ProgramStudiowDetailComponent,
    resolve: {
      programStudiow: ProgramStudiowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.programStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ProgramStudiowUpdateComponent,
    resolve: {
      programStudiow: ProgramStudiowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.programStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ProgramStudiowUpdateComponent,
    resolve: {
      programStudiow: ProgramStudiowResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'appApp.programStudiow.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
