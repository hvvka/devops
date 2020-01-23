import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { DyscyplinaComponent } from './dyscyplina.component';
import { DyscyplinaDetailComponent } from './dyscyplina-detail.component';
import { DyscyplinaUpdateComponent } from './dyscyplina-update.component';
import { DyscyplinaDeleteDialogComponent } from './dyscyplina-delete-dialog.component';
import { dyscyplinaRoute } from './dyscyplina.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(dyscyplinaRoute)],
  declarations: [DyscyplinaComponent, DyscyplinaDetailComponent, DyscyplinaUpdateComponent, DyscyplinaDeleteDialogComponent],
  entryComponents: [DyscyplinaDeleteDialogComponent]
})
export class AppDyscyplinaModule {}
