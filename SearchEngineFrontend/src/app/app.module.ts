import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { ToastrModule } from 'ngx-toastr';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './componentes/header/header.component';
import { InfoTpuComponent } from './componentes/info-tpu/info-tpu.component';
import { IndexadorComponent } from './componentes/indexador/indexador.component';
import { HttpClientModule } from '@angular/common/http';
import { BuscadorComponent } from './componentes/buscador/buscador.component';
import { ResultadosComponent } from './componentes/resultados/resultados.component';
import { BsModalService, ModalModule } from 'ngx-bootstrap/modal';
import { ModalHttpComponent } from './componentes/modal-http/modal-http.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    InfoTpuComponent,
    IndexadorComponent,
    BuscadorComponent,
    ResultadosComponent,
    ModalHttpComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule,
    BrowserAnimationsModule,
    ToastrModule.forRoot(),
    HttpClientModule,
    ModalModule.forRoot()
  ],
  providers: [BsModalService],
  bootstrap: [AppComponent]
})
export class AppModule {  }
