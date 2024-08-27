import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {TipEvaluacije} from "../model/tipEvaluacije";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class TipEvaluacijeService extends GenerickiService<TipEvaluacije>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/tip-evaluacije";
  }
}
