import {PredmetPlanaZaGodinu} from "./predmetPlanaZaGodinu";
import {GodinaStudija} from "./godinaStudija";

export interface PlanZaGodinu {
  id: number,
  godina: number,
  brojSemestara: number,
  godinaStudija: GodinaStudija,
  predmetiPlanaZaGodinu: PredmetPlanaZaGodinu[]
}
