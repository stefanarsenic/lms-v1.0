import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {TipZvanja} from "../model/tipZvanja";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TipZvanjaService extends GenerickiService<TipZvanja>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/tip-zvanja"
  }
}
