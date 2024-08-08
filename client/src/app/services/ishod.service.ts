import { Injectable } from '@angular/core';
import {GenerickiService} from "../../genericko/genericki.service";
import {Ishod} from "../model/ishod";
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class IshodService extends GenerickiService<Ishod>{

  constructor(protected override http: HttpClient) {
    super(http);
    this.putanja="api/ishod"
  }

  deleteIshod(userIds: (number | null)[]): Observable<void> {
    const url = `http://localhost:8080/api/ishod/delete`;
    return this.http.request<void>('DELETE', url, {
      body: userIds
    });
  }

}
