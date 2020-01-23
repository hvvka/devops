import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { EfektKsztalceniaComponent } from './efekt-ksztalcenia.component';
import { EfektKsztalceniaDetailComponent } from './efekt-ksztalcenia-detail.component';
import { EfektKsztalceniaUpdateComponent } from './efekt-ksztalcenia-update.component';
import { EfektKsztalceniaDeleteDialogComponent } from './efekt-ksztalcenia-delete-dialog.component';
import { efektKsztalceniaRoute } from './efekt-ksztalcenia.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(efektKsztalceniaRoute)],
  declarations: [
    EfektKsztalceniaComponent,
    EfektKsztalceniaDetailComponent,
    EfektKsztalceniaUpdateComponent,
    EfektKsztalceniaDeleteDialogComponent
  ],
  entryComponents: [EfektKsztalceniaDeleteDialogComponent]
})
export class AppEfektKsztalceniaModule {}
