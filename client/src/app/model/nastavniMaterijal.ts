import {Nastavnik} from "./nastavnik";

export interface NastavniMaterijal {
  id: number,
  naziv: string,
  autori: Nastavnik[]
}
