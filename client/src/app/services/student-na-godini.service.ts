import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Student} from "../model/student";
import {HttpClient, HttpParams} from "@angular/common/http";
import {StudentNaGodini} from "../model/studentNaGodini";

@Injectable({
  providedIn: 'root'
})
export class StudentNaGodiniService extends GenerickiService<StudentNaGodini>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/student-na-godini";
  }

  getUpisi(params: HttpParams){
    return this.http.get<any[]>(`http://localhost:8080/${this.putanja}/upisi-by-student?${params}`);
  }

  getStudentInfo(params: HttpParams){
    return this.http.get<any>(`http://localhost:8080/api/pohadjanje-predmeta/student-info?${params}`);
  }

  getStudentiByPredmet(params: HttpParams){
    return this.http.get<any[]>(`http://localhost:8080/${this.putanja}/by-predmet?${params}`);
  }

  getAllByStudentId(studentId: number){
    return this.http.get<StudentNaGodini[]>(`http://localhost:8080/${this.putanja}/student/${studentId}`);
  }

  getAllByStudentUsername(username:string){
    return this.http.get<StudentNaGodini[]>(`http://localhost:8080/${this.putanja}/student/${username}/student-na-godini`);

  }
}
