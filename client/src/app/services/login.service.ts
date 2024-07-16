import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {tap} from "rxjs";


@Injectable({
  providedIn: 'root'
})
export class LoginService {


  korisnik=null
  constructor(private http:HttpClient) { }

  login(korisnik:any){
    return this.http.post("http://localhost:8080/api/login",korisnik,{ responseType: 'text' }).pipe(
      tap(
        token=>{
          localStorage.setItem('token', token);
          this.korisnik=JSON.parse(atob(token.split(".")[1]))
        }
      )
    );
  }

  proveraUloga(zahtevaneUloge:string[]){
    if(this.korisnik){
      let zahtevaneUlogeSet=new Set<string>(zahtevaneUloge)
      let role=new Set<string>(this.korisnik["role"])
      console.log(role)
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

}
