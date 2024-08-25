import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {PolozeniPredmet} from "../model/polozeniPredmet";
import {HttpClient} from "@angular/common/http";
import {PlanZaGodinu} from "../model/planZaGodinu";

@Injectable({
  providedIn: 'root'
})
export class PlanZaGodinuService extends GenerickiService<PolozeniPredmet>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/plan-za-godinu"
  }

  findByStudijskiProgramId(studijskiProgramId: number){
    return this.http.get<PlanZaGodinu[]>(`http://localhost:8080/${this.putanja}/by-studijski-program/${studijskiProgramId}`);
  }
  getUslovEspb(studijskiProgramId: number, godina: number){
    return this.http.get<number>(`http://localhost:8080/${this.putanja}/uslov-espb/${studijskiProgramId}/godina/${godina}`);
  }
}
