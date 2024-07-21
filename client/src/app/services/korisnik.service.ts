import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class KorisnikService {

  constructor(private http:HttpClient) { }


  getUserInfo(username: string) {
    let params = new HttpParams().set('username', username);
    return this.http.get("http://localhost:8080/api/username", { params });
  }

  register(korisnik:any){
    return this.http.post("http://localhost:8080/api/register",korisnik)
  }

}