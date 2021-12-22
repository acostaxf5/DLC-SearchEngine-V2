import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { BuscadorComponent } from './componentes/buscador/buscador.component';
import { InfoTpuComponent } from './componentes/info-tpu/info-tpu.component';
import { ResultadosComponent } from './componentes/resultados/resultados.component';

const routes: Routes = [
  { path: "", component: BuscadorComponent },
  { path: "info-tpu", component: InfoTpuComponent },
  { path: "resultados/:consulta", component: ResultadosComponent },
  { path: "**", redirectTo: "", pathMatch: "full" }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {  }
