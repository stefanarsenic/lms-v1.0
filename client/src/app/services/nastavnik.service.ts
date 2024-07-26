import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Nastavnik} from "../model/nastavnik";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class NastavnikService extends GenerickiService<Nastavnik>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/nastavnik"
  }
}
