import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { PrzedmiotComponent } from './przedmiot.component';
import { PrzedmiotDetailComponent } from './przedmiot-detail.component';
import { PrzedmiotUpdateComponent } from './przedmiot-update.component';
import { PrzedmiotDeleteDialogComponent } from './przedmiot-delete-dialog.component';
import { przedmiotRoute } from './przedmiot.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(przedmiotRoute)],
  declarations: [PrzedmiotComponent, PrzedmiotDetailComponent, PrzedmiotUpdateComponent, PrzedmiotDeleteDialogComponent],
  entryComponents: [PrzedmiotDeleteDialogComponent]
})
export class AppPrzedmiotModule {}
