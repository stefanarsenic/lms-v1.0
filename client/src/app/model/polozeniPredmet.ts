import {StudentNaGodini} from "./studentNaGodini";
import {Predmet} from "./predmet";

export interface PolozeniPredmet {
  id: number,
  konacnaOcena: number,
  student?: StudentNaGodini,
  predmet: Predmet
}
