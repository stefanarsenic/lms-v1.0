import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Polaganje} from "../model/polaganje";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PolaganjeService extends GenerickiService<Polaganje>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/polaganje";
  }
}
