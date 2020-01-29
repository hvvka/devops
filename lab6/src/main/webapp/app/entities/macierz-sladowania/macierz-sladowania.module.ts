import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppSharedModule } from 'app/shared/shared.module';
import { MacierzSladowaniaComponent } from './macierz-sladowania.component';
import { macierzSladowaniaRoute } from './macierz-sladowania.route';

@NgModule({
  imports: [AppSharedModule, RouterModule.forChild(macierzSladowaniaRoute)],
  declarations: [MacierzSladowaniaComponent]
})
export class AppMacierzSladowaniaModule {}
