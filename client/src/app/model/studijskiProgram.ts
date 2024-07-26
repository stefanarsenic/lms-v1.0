import {Fakultet} from "./fakultet";
import {Nastavnik} from "./nastavnik";

export interface StudijskiProgram {
  id: number,
  naziv: string,
  opis: string,
  godineTrajanja: number | null,
  fakultet?: Fakultet,
  rukovodilac?: Nastavnik,
}
