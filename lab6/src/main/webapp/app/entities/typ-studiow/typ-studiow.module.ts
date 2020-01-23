import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { TypStudiowComponent } from './typ-studiow.component';
import { TypStudiowDetailComponent } from './typ-studiow-detail.component';
import { TypStudiowUpdateComponent } from './typ-studiow-update.component';
import { TypStudiowDeleteDialogComponent } from './typ-studiow-delete-dialog.component';
import { typStudiowRoute } from './typ-studiow.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(typStudiowRoute)],
  declarations: [TypStudiowComponent, TypStudiowDetailComponent, TypStudiowUpdateComponent, TypStudiowDeleteDialogComponent],
  entryComponents: [TypStudiowDeleteDialogComponent]
})
export class AppTypStudiowModule {}
