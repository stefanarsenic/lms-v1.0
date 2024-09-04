import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Drzava} from "../model/drzava";
import {HttpClient} from "@angular/common/http";
import {Adresa} from "../model/adresa";

@Injectable({
  providedIn: 'root'
})
export class AdresaService extends GenerickiService<Adresa>{


  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/adresa"
  }

}
