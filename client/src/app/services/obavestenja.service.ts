import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Obavestenje} from "../model/obavestenje";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ObavestenjaService extends GenerickiService<Obavestenje>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/obavestenja";
  }

  getObavestenjaByPredmetId(predmetId: number): Observable<Obavestenje[]> {
    return this.http.get<Obavestenje[]>(`http://localhost:8080/api/obavestenja/predmet/${predmetId}`);
  }


}
