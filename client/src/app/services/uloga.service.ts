import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Uloga} from "../model/uloga";
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UlogaService extends GenerickiService<Uloga>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/uloge";
  }
}
