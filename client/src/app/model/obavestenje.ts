import {Fajl} from "./fajl";

export interface Obavestenje{
  id:number|null,
  naslov:string,
  sadrzaj:string,
  vremePostavljanja:Date
  prilozi:Fajl[]

}
