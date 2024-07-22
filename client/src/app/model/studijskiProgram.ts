import {Fakultet} from "./fakultet";
import {Nastavnik} from "./nastavnik";
import {GodinaStudija} from "./godinaStudija";

export interface StudijskiProgram {
  id: number,
  naziv: string,
  opis: string,
  fakultet: Fakultet,
  rukovodilac: Nastavnik,
  godineStudija: GodinaStudija[]
}
