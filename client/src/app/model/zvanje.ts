import {Nastavnik} from "./nastavnik";
import {TipZvanja} from "./tipZvanja";
import {NaucnaOblast} from "./naucnaOblast";

export interface Zvanje {
  id: number,
  datumIzbora: Date,
  datumPrestanka: Date,
  nastavnik: Nastavnik,
  tipZvanja: TipZvanja,
  naucnaOblast: NaucnaOblast
}
