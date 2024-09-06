import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class StudijskiProgramService extends GenerickiService<any>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/studijski-program";
  }

  getAllNotAttended(params: HttpParams){
    return this.http.get<any>(`http://localhost:8080/${this.putanja}/not-attended-by-student?${params}`);
  }
}
