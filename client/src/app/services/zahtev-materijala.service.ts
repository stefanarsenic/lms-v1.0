import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class ZahtevMaterijalaService {

  private apiUrl = "http://localhost:8080/api/zahtevi";

  constructor(private http: HttpClient) { }

  getAllRequests(): Observable<any[]> {
    return this.http.get<any[]>("http://localhost:8080/api/zahtevi/all");
  }

  addZahtev(zahtev: any): Observable<any> {
    return this.http.post(`${this.apiUrl}/submit`, zahtev);
  }
  findByKorisnikId(korisnikId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${korisnikId}`);
  }

  findByUsername(username: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/user/${username}`);
  }

  updateRequestStatus(id: number, req:any): Observable<any> {
    return this.http.put(`${this.apiUrl}/update/${id}`,req);
  }

  delete(id:number):Observable<any> {
    return this.http.delete(`${this.apiUrl}/${id}`);
  }
}
