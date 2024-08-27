import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Semestar} from "../model/semestar";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SemestarService extends GenerickiService<Semestar>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/semestar";
  }


}
