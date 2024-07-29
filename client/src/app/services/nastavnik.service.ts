import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Nastavnik} from "../model/nastavnik";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class NastavnikService extends GenerickiService<Nastavnik>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/nastavnici"
  }

  deleteUsers(userIds: number[]): Observable<void> {
    const url = `http://localhost:8080/api/nastavnici/delete`;
    return this.http.request<void>('DELETE', url, {
      body: userIds
    });
  }

}
