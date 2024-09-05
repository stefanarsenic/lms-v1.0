import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {PolaganjePredmeta} from "../model/polaganjePredmeta";
import {HttpClient, HttpParams} from "@angular/common/http";
import {PolaganjeService} from "./polaganje.service";

@Injectable({
  providedIn: 'root'
})
export class PolaganjePredmetaService extends GenerickiService<PolaganjePredmeta>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/polaganje-predmeta";
  }

  createPolaganje(params: HttpParams, polaganjePredmeta: PolaganjePredmeta){
    return this.http.post<any>(`http://localhost:8080/${this.putanja}/arhiviraj?${params}`, polaganjePredmeta);
  }
}
