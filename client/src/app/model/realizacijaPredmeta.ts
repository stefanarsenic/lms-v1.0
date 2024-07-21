import {Predmet} from "./predmet";
import {EvaluacijaZnanja} from "./evaluacijaZnanja";

export interface RealizacijaPredmeta {
  id: number,
  predmet: Predmet,
  terminNastave: TerminNastave,
  evaluacijaZnanja: EvaluacijaZnanja
}
