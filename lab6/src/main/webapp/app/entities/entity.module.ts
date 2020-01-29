import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'karta-przedmiotu',
        loadChildren: () => import('./karta-przedmiotu/karta-przedmiotu.module').then(m => m.AppKartaPrzedmiotuModule)
      },
      {
        path: 'opiekun-przedmiotu',
        loadChildren: () => import('./opiekun-przedmiotu/opiekun-przedmiotu.module').then(m => m.AppOpiekunPrzedmiotuModule)
      },
      {
        path: 'przedmiot',
        loadChildren: () => import('./przedmiot/przedmiot.module').then(m => m.AppPrzedmiotModule)
      },
      {
        path: 'zajecie',
        loadChildren: () => import('./zajecie/zajecie.module').then(m => m.AppZajecieModule)
      },
      {
        path: 'typ-studiow',
        loadChildren: () => import('./typ-studiow/typ-studiow.module').then(m => m.AppTypStudiowModule)
      },
      {
        path: 'program-studiow',
        loadChildren: () => import('./program-studiow/program-studiow.module').then(m => m.AppProgramStudiowModule)
      },
      {
        path: 'efekt-ksztalcenia',
        loadChildren: () => import('./efekt-ksztalcenia/efekt-ksztalcenia.module').then(m => m.AppEfektKsztalceniaModule)
      },
      {
        path: 'macierz-sladowania',
        loadChildren: () => import('./macierz-sladowania/macierz-sladowania.module').then(m => m.AppMacierzSladowaniaModule)
      },
      {
        path: 'dyscyplina',
        loadChildren: () => import('./dyscyplina/dyscyplina.module').then(m => m.AppDyscyplinaModule)
      },
      {
        path: 'efekt-ministerialny',
        loadChildren: () => import('./efekt-ministerialny/efekt-ministerialny.module').then(m => m.AppEfektMinisterialnyModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class AppEntityModule {}
