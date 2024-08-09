import {Predmet} from "./predmet";
import {EvaluacijaZnanja} from "./evaluacijaZnanja";
import {TerminNastave} from "./terminNastave";
import {Obavestenje} from "./obavestenje";

export interface RealizacijaPredmeta {
  id: number,
  predmet: Predmet,
  terminNastave: TerminNastave[],
  evaluacijaZnanja: EvaluacijaZnanja[]
  obavestenja:Obavestenje[]
}
