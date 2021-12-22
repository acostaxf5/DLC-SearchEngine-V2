import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { BsModalService } from 'ngx-bootstrap/modal';
import { ToastrService } from 'ngx-toastr';
import { BuscadorService } from 'src/app/servicios/buscador.service';
import { ModalHttpComponent } from '../modal-http/modal-http.component';

@Component({
  selector: 'app-buscador',
  templateUrl: './buscador.component.html',
  styleUrls: ['./buscador.component.css']
})
export class BuscadorComponent implements OnInit {

  formularioBuscador: FormGroup;
  limite: string = "15";
  consulta: string;

  constructor(private fb: FormBuilder, private r: Router, private bs: BuscadorService, private ts: ToastrService, private ms: BsModalService) { }

  ngOnInit(): void {
    this.formularioBuscador = this.fb.group({
      consulta: ["", [Validators.required]],
      limite: ["", [Validators.pattern("[0-9]+")]]
    });
  }

  restablecerLimite(): void {
    if (this.formularioBuscador.controls['limite'].value === "") {
      this.limite = "15";
    }
  }

  buscar(): void {
    this.ms.show(ModalHttpComponent, {backdrop: 'static', keyboard: false});

    const limiteAsignado: string = this.formularioBuscador.controls['limite'].value;
    if (limiteAsignado !== "") {
      this.limite = limiteAsignado;
    }
    this.consulta = this.formularioBuscador.controls['consulta'].value;

    this.bs.buscar(this.consulta, this.limite).subscribe(
      respuesta => {
        this.bs.setArchivos(respuesta);
        this.ms.hide();
        this.r.navigate(['/resultados', this.consulta]);
      },
      error => {
        this.ms.hide();
        this.ts.error("NO SE PUEDE ESTABLECER UNA CONEXIÓN CON EL SERVIDOR", "ERROR DE CONEXIÓN", {
          timeOut: 5000,
          positionClass: "toast-bottom-full-width"
        });
      });
  }

}
