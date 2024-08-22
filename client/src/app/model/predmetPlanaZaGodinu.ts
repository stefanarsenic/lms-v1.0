import {PlanZaGodinu} from "./planZaGodinu";
import {Predmet} from "./predmet";

export interface PredmetPlanaZaGodinu {
  id: number,
  semestar: number,
  planZaGodinu: PlanZaGodinu,
  predmet: Predmet
}
