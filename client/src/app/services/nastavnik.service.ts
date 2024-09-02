import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Nastavnik} from "../model/nastavnik";
import {HttpClient, HttpParams} from "@angular/common/http";
import {Observable} from "rxjs";
import {RegistrovaniKorisnik} from "../model/registrovaniKorisnik";

@Injectable({
  providedIn: 'root'
})
export class NastavnikService extends GenerickiService<Nastavnik>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/nastavnici"
  }

  getByKorisnickoIme(params: HttpParams){
    return this.http.get<Nastavnik>(`http://localhost:8080/${this.putanja}/korisnickoIme?${params}`);
  }

  deleteUsers(userIds: number[]): Observable<void> {
    const url = `http://localhost:8080/api/nastavnici/delete`;
    return this.http.request<void>('DELETE', url, {
      body: userIds
    });
  }

  updateNastavnik(id: number | null, korisnik: Nastavnik){
    return this.http.put(`http://localhost:8080/api/nastavnici/azuriraj/${id}`,korisnik)
  }

}
