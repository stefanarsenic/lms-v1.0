import { Injectable } from '@angular/core';
import {TerminNastave} from "../model/terminNastave";
import {HttpClient, HttpParams} from "@angular/common/http";
import {outputAst} from "@angular/compiler";
import {GenerickiService} from "../../genericko/genericki.service";
import {EvaluacijaZnanja} from "../model/evaluacijaZnanja";

@Injectable({
  providedIn: 'root'
})
export class EvaluacijaZnanjaService extends GenerickiService<EvaluacijaZnanja>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/evaluacija-znanja"
  }

  getAllByPredmet(predmetId: number){
    return this.http.get<EvaluacijaZnanja[]>(`http://localhost:8080/${this.putanja}/predmet/${predmetId}`);
  }

  createByPredmet(predmetId: number, evaluacijaZnanja: EvaluacijaZnanja, params: HttpParams){
    return this.http.post(`http://localhost:8080/${this.putanja}/predmet/${predmetId}?${params}`, evaluacijaZnanja);
  }
}
