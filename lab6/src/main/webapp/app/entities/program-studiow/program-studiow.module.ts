import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { ProgramStudiowComponent } from './program-studiow.component';
import { ProgramStudiowDetailComponent } from './program-studiow-detail.component';
import { ProgramStudiowUpdateComponent } from './program-studiow-update.component';
import { ProgramStudiowDeleteDialogComponent } from './program-studiow-delete-dialog.component';
import { programStudiowRoute } from './program-studiow.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(programStudiowRoute)],
  declarations: [
    ProgramStudiowComponent,
    ProgramStudiowDetailComponent,
    ProgramStudiowUpdateComponent,
    ProgramStudiowDeleteDialogComponent
  ],
  entryComponents: [ProgramStudiowDeleteDialogComponent]
})
export class AppProgramStudiowModule {}
