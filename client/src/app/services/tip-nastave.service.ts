import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {TipNastave} from "../model/tipNastave";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TipNastaveService extends GenerickiService<TipNastave>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/tip-nastave";
  }
}
