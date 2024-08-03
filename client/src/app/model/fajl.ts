import {Obavestenje} from "./obavestenje";

export interface Fajl{
  id:number|null,
  opis:string,
  url:string,
  obvestenje:Obavestenje
}
