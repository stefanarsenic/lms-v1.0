import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";
import {Router} from "@angular/router";
import {isTokenExpired} from "./auth.utils";


@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private token: string = 'token';
  korisnik=null
  constructor(private http:HttpClient, private router: Router) { }

  login(korisnik:any){
    return this.http.post("http://localhost:8080/api/login",korisnik,{ responseType: 'text' }).pipe(
      tap(
        token=>{
          localStorage.setItem('token', token);
          this.korisnik=JSON.parse(atob(token.split(".")[1]));
          this.router.navigate(["/main"]);
        }
      )
    );
  }

  proveraUloga(zahtevaneUloge:string[]){
    const token: string | null = localStorage.getItem('token');
    this.korisnik = (token) ?  JSON.parse(atob(token.split(".")[1])) : null;

    if(this.korisnik){
      let zahtevaneUlogeSet=new Set<string>(zahtevaneUloge)
      let role=new Set<string>(this.korisnik["role"])
      let rezultatePreseka=new Set<string>([])

      for(let r of role){
        if(zahtevaneUlogeSet.has(r)){
          rezultatePreseka.add(r)
        }
      }

      if(rezultatePreseka.size>0){
        return true
      }
    }

    return false
  }
  public getToken(): string | null {
    return localStorage.getItem(this.token);
  }

  public saveToken(token: string): void {
    localStorage.setItem(this.token, token);
  }

  public clearToken(): void {
    localStorage.removeItem(this.token);
  }

  public isAuthenticated(): boolean {
    const token = this.getToken();
    return token != null && !isTokenExpired(token);
  }
}
