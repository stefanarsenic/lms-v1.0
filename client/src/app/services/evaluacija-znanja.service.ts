import { Injectable } from '@angular/core';
import {TerminNastave} from "../model/terminNastave";
import {HttpClient, HttpParams} from "@angular/common/http";
import {outputAst} from "@angular/compiler";
import {GenerickiService} from "../../genericko/genericki.service";
import {EvaluacijaZnanja} from "../model/evaluacijaZnanja";
import {TipPolaganja} from "../model/tipPolaganja";
import {Fajl} from "../model/fajl";

@Injectable({
  providedIn: 'root'
})
export class EvaluacijaZnanjaService extends GenerickiService<EvaluacijaZnanja>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/evaluacija-znanja"
  }

  updateInstrumentEvaluacije(ezId: number, fajl: Fajl){
    return this.http.put<EvaluacijaZnanja >(`http://localhost:8080/${this.putanja}/${ezId}/instrument-evaluacije`, fajl);
  }

  getAllByNastavnikAndPredmet(params: HttpParams){
    return this.http.get<EvaluacijaZnanja[]>(`http://localhost:8080/${this.putanja}/by-nastavnik-and-predmet?${params}`);
  }

  getTipoviPolaganja(){
    return this.http.get<TipPolaganja[]>(`http://localhost:8080/${this.putanja}/tipovi-polaganja-k`);
  }

  getNepolaganiKolokvijumiByPredmetAndStudent(params: HttpParams){
    return this.http.get<EvaluacijaZnanja[]>(`http://localhost:8080/${this.putanja}/nepolagani-kolokvijumi-by-student?${params}`);
  }

  createIspit(evaluacijaZnanja: EvaluacijaZnanja, params: HttpParams){
    return this.http.post(`http://localhost:8080/${this.putanja}/ispit?${params}`, evaluacijaZnanja);
  }

  getAllIspitiByStudijskiProgram(params: HttpParams){
    return this.http.get<EvaluacijaZnanja[]>(`http://localhost:8080/${this.putanja}/ispiti?${params}`);
  }

  getAllByPredmet(predmetId: number){
    return this.http.get<EvaluacijaZnanja[]>(`http://localhost:8080/${this.putanja}/predmet/${predmetId}`);
  }

  createByPredmet(predmetId: number, evaluacijaZnanja: EvaluacijaZnanja, params: HttpParams){
    return this.http.post(`http://localhost:8080/${this.putanja}/predmet/${predmetId}?${params}`, evaluacijaZnanja);
  }
}
