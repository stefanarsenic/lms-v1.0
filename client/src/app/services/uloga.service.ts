import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Uloga} from "../model/uloga";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class UlogaService extends GenerickiService<Uloga>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja = "api/uloge";
  }

  deleteRoles(userIds: number[]): Observable<void> {
    const url = `http://localhost:8080/api/uloge/delete`;
    return this.http.request<void>('DELETE', url, {
      body: userIds
    });
  }
}
