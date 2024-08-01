import {Mesto} from "./mesto";

export interface Drzava {
  id: number |null,
  naziv: string,
  mesta: Mesto[]
}
