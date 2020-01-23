import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { KartaPrzedmiotuComponent } from './karta-przedmiotu.component';
import { KartaPrzedmiotuDetailComponent } from './karta-przedmiotu-detail.component';
import { KartaPrzedmiotuUpdateComponent } from './karta-przedmiotu-update.component';
import { KartaPrzedmiotuDeleteDialogComponent } from './karta-przedmiotu-delete-dialog.component';
import { kartaPrzedmiotuRoute } from './karta-przedmiotu.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(kartaPrzedmiotuRoute)],
  declarations: [
    KartaPrzedmiotuComponent,
    KartaPrzedmiotuDetailComponent,
    KartaPrzedmiotuUpdateComponent,
    KartaPrzedmiotuDeleteDialogComponent
  ],
  entryComponents: [KartaPrzedmiotuDeleteDialogComponent]
})
export class AppKartaPrzedmiotuModule {}
