import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { EfektMinisterialnyComponent } from './efekt-ministerialny.component';
import { EfektMinisterialnyDetailComponent } from './efekt-ministerialny-detail.component';
import { EfektMinisterialnyUpdateComponent } from './efekt-ministerialny-update.component';
import { EfektMinisterialnyDeleteDialogComponent } from './efekt-ministerialny-delete-dialog.component';
import { efektMinisterialnyRoute } from './efekt-ministerialny.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(efektMinisterialnyRoute)],
  declarations: [
    EfektMinisterialnyComponent,
    EfektMinisterialnyDetailComponent,
    EfektMinisterialnyUpdateComponent,
    EfektMinisterialnyDeleteDialogComponent
  ],
  entryComponents: [EfektMinisterialnyDeleteDialogComponent]
})
export class AppEfektMinisterialnyModule {}
