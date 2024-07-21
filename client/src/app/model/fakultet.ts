import {Adresa} from "./adresa";
import {Univerzitet} from "./univerzitet";
import {Nastavnik} from "./nastavnik";
import {StudijskiProgram} from "./studijskiProgram";

export interface Fakultet {
  id: number,
  naziv: string,
  adresa: Adresa,
  univerzitet: Univerzitet,
  dekan: Nastavnik,
  studijskiProgrami: StudijskiProgram
}
