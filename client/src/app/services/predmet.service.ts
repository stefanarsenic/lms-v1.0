import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Predmet} from "../model/predmet";
import {Nastavnik} from "../model/nastavnik";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class PredmetService extends GenerickiService<any>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/predmet"
  }

  getEsbpByPredmetId(predmetId: number){
    return this.http.get<number>(`http://localhost:8080/${this.putanja}/${predmetId}/esbp`);
  }
  getPredmetByStudijskiProgramAndGodina(studijskiProgramId: number, godina: number){
    return this.http.get<any[]>("http://localhost:8080/" + this.putanja + `/studijski-program/${studijskiProgramId}/godina/${godina}`);
  }
  getPredmetiByStudijskiProgram(studijskiProgramId: number){
    return this.http.get<any[]>("http://localhost:8080/" + this.putanja + `/studijski-program/${studijskiProgramId}`);
  }
  createPlanWithPredmeti(predmeti: any[], studijskiProgramId: number, godina: number){
    return this.http.post(`http://localhost:8080/api/predmet-plana-za-godinu/with-predmeti/${studijskiProgramId}/${godina}`, predmeti);
  }

  getPredmetByNastavnik(nastavnik: string): Observable<Predmet[]> {
    const params = new HttpParams().set('korisnickoIme',nastavnik);
    return this.http.get<Predmet[]>(`http://localhost:8080/${this.putanja}/predmeti`, { params });
  }
}
