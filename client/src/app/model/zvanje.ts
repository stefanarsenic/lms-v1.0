import {Nastavnik} from "./nastavnik";
import {TipZvanja} from "./tipZvanja";
import {NaucnaOblast} from "./naucnaOblast";

export interface Zvanje {
  id: number |null,
  datumIzbora: Date|null,
  datumPrestanka: Date |null,
  nastavnik: Nastavnik | null,
  tipZvanja: TipZvanja,
  naucnaOblast: NaucnaOblast|null
}
