import {Predmet} from "./predmet";
import {ObrazovniCilj} from "./obrazovniCilj";

export interface Ishod {
  id: number | null,
  opis: string,
  predmet: Predmet | null,
  obrazovniCiljevi?: ObrazovniCilj[]
}
