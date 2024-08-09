import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {RealizacijaPredmeta} from "../model/realizacijaPredmeta";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class RealizacijaPredmetaService extends GenerickiService<RealizacijaPredmeta>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/realizacija-predmeta"
  }
}
