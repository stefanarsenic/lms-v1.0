import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Polaganje} from "../model/polaganje";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PolaganjeService extends GenerickiService<Polaganje>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/polaganje";
  }

  createPolaganjeKolokvijuma(params: HttpParams, polaganje: any){
    return this.http.post<Polaganje>(`http://localhost:8080/${this.putanja}/kolokvijum/by-student?${params}`, polaganje);
  }

  updatePolaganje(params: HttpParams, polaganje: any){
    return this.http.put<Polaganje>(`http://localhost:8080/${this.putanja}/update-bodovi?${params}`, polaganje);
  }

  getLatestPolaganjaByStudent(params: HttpParams){
    return this.http.get<Polaganje[]>(`http://localhost:8080/${this.putanja}/latest-polaganja-by-student?${params}`);
  }

  getPrijavljeniIspitiStudenta(params: HttpParams){
    return this.http.get<any[]>(`http://localhost:8080/${this.putanja}/prijavljeni-ispiti-by-student?${params}`);
  }

  createPolaganjeIspita(params: HttpParams){
    return this.http.post<Polaganje>(`http://localhost:8080/${this.putanja}/by-student?${params}`, null);
  }
}
