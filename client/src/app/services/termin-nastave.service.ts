import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {TerminNastave} from "../model/terminNastave";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TerminNastaveService extends GenerickiService<TerminNastave> {

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/termin-nastave";
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
