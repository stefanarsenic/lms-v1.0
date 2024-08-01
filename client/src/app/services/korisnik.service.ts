import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from "@angular/common/http";
import {RegistrovaniKorisnik} from "../model/registrovaniKorisnik";
import {Observable} from "rxjs";


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

  create(korisnik:any){
    return this.http.post("http://localhost:8080/api/korisnici/dodaj",korisnik)
  }

  getAll(){
    return this.http.get("http://localhost:8080/api/korisnici")
  }

  delete(id:number){
    return this.http.delete(`http://localhost:8080/api/korisnici/${id}`);
  }

  update(id:number,korisnik:RegistrovaniKorisnik){
    return this.http.put(`http://localhost:8080/api/korisnici/azuriaj/${id}`,korisnik)
  }

  deleteUsers(userIds: number[]): Observable<void> {
    const url = `http://localhost:8080/api/korisnici/delete`;
    return this.http.request<void>('DELETE', url, {
      body: userIds
    });
  }

}
