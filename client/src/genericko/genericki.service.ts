import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GenerickiService<T> {

  constructor(private http:HttpClient) {

  }

  putanja:string=""
  getAll(){
    return this.http.get<T[]>(`http://localhost:3000/${this.putanja}`);
  }

  create(objekat:T){
    return this.http.post(`http://localhost:3000/${this.putanja}`,objekat);
  }

  delete(id:number){
    return this.http.delete(`http://localhost:3000/${this.putanja}/${id}`);
  }

  update(id:number,objekat:T){
    return this.http.put(`http://localhost:3000/${this.putanja}/${id}`,objekat)
  }

}
