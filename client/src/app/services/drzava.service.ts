import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Drzava} from "../model/drzava";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class DrzavaService extends GenerickiService<Drzava>{


  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/drzava"
  }
}
