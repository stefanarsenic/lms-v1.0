import {Nastavnik} from "./nastavnik";
import {Fakultet} from "./fakultet";
import {Adresa} from "./adresa";

export interface Univerzitet {
  id: number,
  naziv: string,
  datumOsnivanja: any,
  adresa: Adresa,
  fakulteti: Fakultet[],
  rektor: Nastavnik
}
