import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Student} from "../model/student";
import {HttpClient} from "@angular/common/http";
import {StudentNaGodini} from "../model/studentNaGodini";

@Injectable({
  providedIn: 'root'
})
export class StudentNaGodiniService extends GenerickiService<StudentNaGodini>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/student-na-godini";
  }

  getAllByStudentId(studentId: number){
    return this.http.get<StudentNaGodini[]>(`http://localhost:8080/${this.putanja}/student/${studentId}`);
  }
}
