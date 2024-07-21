import {Student} from "./student";
import {GodinaStudija} from "./godinaStudija";

export interface StudentNaGodini {
  id: number,
  datumUpisa: Date,
  brojIndeksa: string,
  student: Student,
  godinaStudija: GodinaStudija
}
