import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { IndexadorService } from 'src/app/servicios/indexador.service';
import { ModalHttpComponent } from '../modal-http/modal-http.component';

@Component({
  selector: 'app-indexador',
  templateUrl: './indexador.component.html',
  styleUrls: ['./indexador.component.css']
})
export class IndexadorComponent implements OnInit {

  archivo: File;
  formularioArchivo: FormGroup;
  textoArchivo: string;

  constructor(private fb: FormBuilder, private ts: ToastrService, private is: IndexadorService, private ms: BsModalService) {  }

  ngOnInit(): void {
    this.textoArchivo = "SELECCIONE UN ARCHIVO";
    this.formularioArchivo = this.fb.group({
      archivo: ["", [Validators.required]]
    });
  }

  onFileChange(event) {
    this.archivo = event.target.files[0];
    this.textoArchivo = this.archivo ? this.archivo.name : "SELECCIONE UN ARCHIVO";
  }

  reestablecerFormulario() {
    this.archivo = null;
    this.textoArchivo = "SELECCIONE UN ARCHIVO";
    this.formularioArchivo.reset();
  }

  indexarArchivo() {
    this.ms.show(ModalHttpComponent, {backdrop: 'static', keyboard: false});
    
    this.is.indexar(this.archivo).subscribe(
      respuesta => {
        this.ms.hide();

        if (respuesta) {
          this.ts.success("ARCHIVO \"" + this.archivo.name + "\" INDEXADO", "INDEXACIÓN FINALIZADA", {
            timeOut: 5000,
            positionClass: "toast-bottom-full-width"
          });
        } else {
          this.ts.error("ARCHIVO \"" + this.archivo.name + "\" EXISTENTE", "INDEXACIÓN FALLIDA", {
            timeOut: 5000,
            positionClass: "toast-bottom-full-width"
          });
        }

        this.reestablecerFormulario();
      },
      error => {
        this.ms.hide();
        
        this.ts.error("NO SE PUEDE ESTABLECER UNA CONEXIÓN CON EL SERVIDOR", "ERROR DE CONEXIÓN", {
          timeOut: 5000,
          positionClass: "toast-bottom-full-width"
        });
        
        this.reestablecerFormulario();
      });
  }

}
