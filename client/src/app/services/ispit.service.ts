import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Ispit} from "../model/ispit";
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class IspitService extends GenerickiService<Ispit>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/ispit"
  }

  getAllByIspitniRokAndStudijskiProgram(ispitniRokId: number, studijskiProgramId: number){
    return this.http.get<Ispit[]>(`http://localhost:8080/${this.putanja}/ispitni-rok/${ispitniRokId}/studijski-program/${studijskiProgramId}`);
  }

  createIspit(objekat:Ispit, params: HttpParams){
    return this.http.post(`http://localhost:8080/${this.putanja}/create?${params}`, objekat);
  }

  updatePocetakIKraj(id: number, pocetak: Date, kraj: Date){
    return this.http.put(`http://localhost:8080/${this.putanja}/${id}/datumi`, {
      pocetakIspita: pocetak,
      krajIspita: kraj
    });
  }

}
