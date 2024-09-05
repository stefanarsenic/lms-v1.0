import {StudentNaGodini} from "./studentNaGodini";
import {Predmet} from "./predmet";
import {Polaganje} from "./polaganje";

export interface PolaganjePredmeta {
  id: number,
  konacnaOcena: number,
  student?: StudentNaGodini,
  predmet?: Predmet,
  polaganja: Polaganje[]
}
