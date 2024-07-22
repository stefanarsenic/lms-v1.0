import {PlanZaGodinu} from "./planZaGodinu";
import {StudijskiProgram} from "./studijskiProgram";
import {Student} from "./student";

export interface GodinaStudija {
  id: number,
  godina: Date,
  studenti: Student[],
  planoviZaGodine: PlanZaGodinu[],
  studijskiProgram: StudijskiProgram
}
