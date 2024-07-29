import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {NaucnaOblast} from "../model/naucnaOblast";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class NacunaOblastService extends GenerickiService<NaucnaOblast>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/naucna-oblast"
  }
}
