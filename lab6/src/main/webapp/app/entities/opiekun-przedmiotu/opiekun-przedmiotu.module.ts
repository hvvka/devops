import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { OpiekunPrzedmiotuComponent } from './opiekun-przedmiotu.component';
import { OpiekunPrzedmiotuDetailComponent } from './opiekun-przedmiotu-detail.component';
import { OpiekunPrzedmiotuUpdateComponent } from './opiekun-przedmiotu-update.component';
import { OpiekunPrzedmiotuDeleteDialogComponent } from './opiekun-przedmiotu-delete-dialog.component';
import { opiekunPrzedmiotuRoute } from './opiekun-przedmiotu.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(opiekunPrzedmiotuRoute)],
  declarations: [
    OpiekunPrzedmiotuComponent,
    OpiekunPrzedmiotuDetailComponent,
    OpiekunPrzedmiotuUpdateComponent,
    OpiekunPrzedmiotuDeleteDialogComponent
  ],
  entryComponents: [OpiekunPrzedmiotuDeleteDialogComponent]
})
export class AppOpiekunPrzedmiotuModule {}
