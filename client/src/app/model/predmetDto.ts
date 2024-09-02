import {Nastavnik} from "./nastavnik";

export interface PredmetDto {
  id: number | null,
  naziv: string | null,
  espb: number | null,
  obavezan: boolean | null,
  brojPredavanja: number | null,
  brojVezbi: number | null,
  drugiObliciNastave: number | null,
  istrazivackiRad: number | null,
  ostaliCasovi: number | null,
  nastavnik: Nastavnik | null,
  asistent: Nastavnik | null,
}
