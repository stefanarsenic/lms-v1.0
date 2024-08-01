import {Drzava} from "./drzava";

export interface Mesto {
  id: number | null,
  naziv: string,
  drzava: Drzava
}
