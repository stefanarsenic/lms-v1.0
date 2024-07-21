import {PlanZaGodinu} from "./planZaGodinu";
import {StudijskiProgram} from "./studijskiProgram";

export interface GodinaStudija {
  id: number,
  godina: Date,
  studenti: Student[],
  planoviZaGodine: PlanZaGodinu[],
  studijskiProgram: StudijskiProgram
}
