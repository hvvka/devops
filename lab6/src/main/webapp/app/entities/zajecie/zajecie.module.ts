import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { ZajecieComponent } from './zajecie.component';
import { ZajecieDetailComponent } from './zajecie-detail.component';
import { ZajecieUpdateComponent } from './zajecie-update.component';
import { ZajecieDeleteDialogComponent } from './zajecie-delete-dialog.component';
import { zajecieRoute } from './zajecie.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(zajecieRoute)],
  declarations: [ZajecieComponent, ZajecieDetailComponent, ZajecieUpdateComponent, ZajecieDeleteDialogComponent],
  entryComponents: [ZajecieDeleteDialogComponent]
})
export class AppZajecieModule {}
