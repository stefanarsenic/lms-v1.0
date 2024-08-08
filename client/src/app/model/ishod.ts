import {Predmet} from "./predmet";

export interface Ishod {
  id: number | null,
  opis: string,
  predmet: Predmet | null
}
