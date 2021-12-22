import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Archivo } from 'src/app/entidades/archivo';
import { ArchivoService } from 'src/app/servicios/archivo.service';
import { BuscadorService } from 'src/app/servicios/buscador.service';
import { saveAs } from 'file-saver';
import { BsModalService } from 'ngx-bootstrap/modal';
import { ModalHttpComponent } from '../modal-http/modal-http.component';

@Component({
  selector: 'app-resultados',
  templateUrl: './resultados.component.html',
  styleUrls: ['./resultados.component.css']
})
export class ResultadosComponent implements OnInit {

  consulta: string;
  archivos: Archivo[];
  cantidad: number = 0;
  textoResultado: string;

  constructor(private ar: ActivatedRoute, private bs: BuscadorService, private as: ArchivoService, private ms: BsModalService) {  }

  ngOnInit(): void {
    this.archivos = this.bs.getArchivos();
    this.cantidad = this.archivos.length;
    this.textoResultado = this.cantidad === 1 ? "1 RESULTADO" : this.cantidad + " RESULTADOS";
    this.consulta = this.ar.snapshot.paramMap.get("consulta");
  }

  obtenerArchivo(archivo: string): void {
    this.ms.show(ModalHttpComponent, {backdrop: 'static', keyboard: false});
    this.as.obtenerArchivo(archivo).subscribe(response => {
      this.ms.hide();
      saveAs(response, archivo);
    });
  }

}
