import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {PredmetPlanaZaGodinu} from "../model/predmetPlanaZaGodinu";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class PredmetPlanaZaGodinuService extends GenerickiService<PredmetPlanaZaGodinu>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/predmet-plana-za-godinu";
  }

  getByPlanZaGodinu(planZaGodinuId: number){
    return this.http.get<PredmetPlanaZaGodinu[]>(`http://localhost:8080/${this.putanja}/by-plan-za-godinu/${planZaGodinuId}`);
  }

  createInBatch(planZaGodinuId: number, predmetiPlanaZaGodinu: PredmetPlanaZaGodinu[]){
    return this.http.post(`http://localhost:8080/${this.putanja}/in-batch/${planZaGodinuId}`, predmetiPlanaZaGodinu);
  }
}
