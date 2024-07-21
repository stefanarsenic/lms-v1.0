import {Nastavnik} from "./nastavnik";
import {Ishod} from "./ishod";

export interface Predmet {
  id: number,
  naziv: string,
  esbp: number,
  obavezan: boolean,
  brojPredavanja: number,
  brojVezbi: number,
  drugiObliciNastave: number,
  istrazivackiRad: number,
  ostaliCasovi: number,
  nastavnik: Nastavnik,
  asistent: Nastavnik,
  silabus: Ishod[],
  preduslov: Predmet[],
  planovi: PredmetPlanaZaGodinu[]
}
