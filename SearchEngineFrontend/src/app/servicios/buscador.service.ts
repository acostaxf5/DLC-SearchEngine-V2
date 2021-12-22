import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Archivo } from '../entidades/archivo';

@Injectable({
  providedIn: 'root'
})
export class BuscadorService {

  baseUrl: string = environment.conexionWebApi;
  private archivos: Archivo[];

  constructor(private http: HttpClient) { }

  buscar(consulta: string, limite: string): Observable<Archivo[]> {
    return this.http.get<Archivo[]>(this.baseUrl + "buscador/buscar/" + consulta + "/" + limite);
  }

  setArchivos(archivos: Archivo[]): void {
    this.archivos = archivos;
  }

  getArchivos(): Archivo[] {
    return this.archivos;
  }

}
