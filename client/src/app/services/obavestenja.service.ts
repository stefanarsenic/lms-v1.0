import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Obavestenje} from "../model/obavestenje";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class ObavestenjaService extends GenerickiService<Obavestenje>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/obavestenja";
  }
}
