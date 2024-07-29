import {Student} from "./student";
import {GodinaStudija} from "./godinaStudija";
import {StudijskiProgram} from "./studijskiProgram";

export interface StudentNaGodini {
  id: number,
  datumUpisa: Date,
  brojIndeksa: string,
  godinaStudija: number,
  student: Student,
  studijskiProgram: StudijskiProgram
}
