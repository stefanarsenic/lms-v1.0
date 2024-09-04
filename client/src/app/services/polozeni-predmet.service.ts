import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {PolozeniPredmet} from "../model/polozeniPredmet";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PolozeniPredmetService extends GenerickiService<PolozeniPredmet>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/pohadjanje-predmeta";
  }

  getByStudentId(studentId: number){
    return this.http.get<PolozeniPredmet[]>(`http://localhost:8080/${this.putanja}/student/${studentId}`);
  }
  getProsecnaOcenaByStudentId(studentId: number){
    return this.http.get<number>(`http://localhost:8080/${this.putanja}/prosecna-ocena/student/${studentId}`);
  }
  getUkupnoEspbByStudentId(studentId: number){
    return this.http.get<number>(`http://localhost:8080/${this.putanja}/ukupno-espb/student/${studentId}`);
  }
}
