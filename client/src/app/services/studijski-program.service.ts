import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class StudijskiProgramService extends GenerickiService<any>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/studijski-program";
  }
}