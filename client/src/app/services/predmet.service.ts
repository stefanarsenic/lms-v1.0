import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PredmetService extends GenerickiService<any>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/predmet"
  }

  getPredmetByStudijskiProgramAndGodina(studijskiProgramId: number, godina: number){
    return this.http.get<any[]>("http://localhost:8080/" + this.putanja + `/studijski-program/${studijskiProgramId}/godina/${godina}`);
  }

  getPredmetiByStudijskiProgram(studijskiProgramId: number){
    return this.http.get<any[]>("http://localhost:8080/" + this.putanja + `/studijski-program/${studijskiProgramId}`);
  }

  createPlanWithPredmeti(predmeti: any[], studijskiProgramId: number, godina: number){
    return this.http.post(`http://localhost:8080/api/predmet-plana-za-godinu/with-predmeti/${studijskiProgramId}/${godina}`, predmeti);
  }
}
