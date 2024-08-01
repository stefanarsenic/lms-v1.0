import {Mesto} from "./mesto";

export interface Adresa {
  id: number| null,
  ulica: string,
  broj: string,
  mesto: Mesto
}
