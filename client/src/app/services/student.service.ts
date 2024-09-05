import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Student} from "../model/student";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class StudentService extends GenerickiService<Student>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/student"
  }

  getNazivDrzaveByStudentId(studentId: number){
    return this.http.get<string>(`http://localhost:8080/${this.putanja}/${studentId}/drzava`, { responseType: 'text' as 'json' });
  }

  dodaj(objekat:Student){
    return this.http.post(`http://localhost:8080/${this.putanja}/dodaj`,objekat);
  }
}
