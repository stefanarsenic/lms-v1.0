import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Predmet} from "../model/predmet";
import {Nastavnik} from "../model/nastavnik";
import {Observable} from "rxjs";
import {Student} from "../model/student";

@Injectable({
  providedIn: 'root'
})
export class PredmetService extends GenerickiService<any>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/predmet"
  }

  getPredmetiNotExistingInIspitiByStudijskiProgram(studijskiProgramId: number){
    return this.http.get<Predmet[]>(`http://localhost:8080/${this.putanja}/not-existing-in-ispiti/${studijskiProgramId}`);
  }
  getEsbpByPredmetId(predmetId: number){
    return this.http.get<number>(`http://localhost:8080/${this.putanja}/${predmetId}/esbp`);
  }
  getPredmetByStudijskiProgramAndSemestar(studijskiProgramId: number, semestar: number){
    return this.http.get<any[]>("http://localhost:8080/" + this.putanja + `/studijski-program/${studijskiProgramId}/semestar/${semestar}`);
  }
  getPredmetByStudijskiProgramAndGodina(studijskiProgramId: number, godina: number){
    return this.http.get<any[]>("http://localhost:8080/" + this.putanja + `/studijski-program/${studijskiProgramId}/godina/${godina}`);
  }
  getPredmetiByStudijskiProgram(studijskiProgramId: number){
    return this.http.get<any[]>("http://localhost:8080/" + this.putanja + `/studijski-program/${studijskiProgramId}`);
  }
  getPredmetByNastavnik(nastavnik: string): Observable<Predmet[]> {
    const params = new HttpParams().set('korisnickoIme',nastavnik);
    return this.http.get<Predmet[]>(`http://localhost:8080/${this.putanja}/predmeti`, { params });
  }
  getStudentiByPremdet(id:number): Observable<Student[]>{
    const params = new HttpParams().set('predmetId',id);
    return this.http.get<Student[]>(`http://localhost:8080/${this.putanja}/${id}/students`, { params });
  }
}
