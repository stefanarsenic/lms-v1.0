import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {TerminNastave} from "../model/terminNastave";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Ishod} from "../model/ishod";

@Injectable({
  providedIn: 'root'
})
export class TerminNastaveService extends GenerickiService<TerminNastave> {

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/termin-nastave";
  }

  updateIshod(terminNastaveId: number, params: HttpParams, ishod: Ishod){
    return this.http.put<TerminNastave>(`http://localhost:8080/${this.putanja}/${terminNastaveId}/ishod?${params}`, ishod);
  }

  getAllByNastavnikAndPredmet(params: HttpParams){
    return this.http.get<TerminNastave[]>(`http://localhost:8080/${this.putanja}/by-nastavnik-and-predmet?${params}`);
  }

  deleteGroup(terminNastaveId: number){
    return this.http.delete(`http://localhost:8080/${this.putanja}/delete-all-by-realizacija-predmeta/${terminNastaveId}`)
  }

  createByPredmet(predmetId: number, terminNastave: TerminNastave){
    return this.http.post(`http://localhost:8080/${this.putanja}/predmet/${predmetId}`, terminNastave);
  }

  createByPredmetRecurring(predmetId: number, terminNastave: TerminNastave){
    return this.http.post(`http://localhost:8080/${this.putanja}/predmet/${predmetId}/recurring`, terminNastave);
  }

  getAllByPredmet(predmetId: number){
    return this.http.get<TerminNastave[]>(`http://localhost:8080/${this.putanja}/predmet/${predmetId}`);
  }

}
