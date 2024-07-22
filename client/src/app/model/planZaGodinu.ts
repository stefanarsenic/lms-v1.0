import {PredmetPlanaZaGodinu} from "./predmetPlanaZaGodinu";
import {GodinaStudija} from "./godinaStudija";

export interface PlanZaGodinu {
  id: number,
  godina: number,
  godinaStudija: GodinaStudija,
  predmetiPlanaZaGodinu: PredmetPlanaZaGodinu[]
}
