import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Univerzitet} from "../model/univerzitet";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UniverzitetService extends GenerickiService<Univerzitet>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/univerzitet";
  }

}
