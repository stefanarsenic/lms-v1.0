import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {GenerickiService} from "../../genericko/genericki.service";
import {IspitniRok} from "../model/ispitniRok";

@Injectable({
  providedIn: 'root'
})
export class IspitniRokService extends GenerickiService<IspitniRok>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/ispitni-rok";
  }
}
