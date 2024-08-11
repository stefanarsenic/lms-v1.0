import {Student} from "./student";
import {GodinaStudija} from "./godinaStudija";
import {StudijskiProgram} from "./studijskiProgram";
import {Predmet} from "./predmet";

export interface StudentNaGodini {
  id: number | null,
  datumUpisa: Date,
  brojIndeksa: string,
  godinaStudija: number,
  student: Student,
  studijskiProgram: StudijskiProgram,
  datumZavrsetka?: Date
  predmeti:Predmet[]
}
