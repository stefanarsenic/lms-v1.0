import {Fajl} from "./fajl";
import {RealizacijaPredmeta} from "./realizacijaPredmeta";

export interface Obavestenje{
  id:number|null,
  naslov:string,
  sadrzaj:string,
  vremePostavljanja:Date,
  prilozi:Fajl[],
  realizacijaPredmeta:RealizacijaPredmeta|null
}
