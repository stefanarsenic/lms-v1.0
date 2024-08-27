import {Obavestenje} from "./obavestenje";

export interface Fajl{
  id:number|null,
  sifra: string,
  opis:string,
  url?:string,
  obvestenje?:Obavestenje
}
