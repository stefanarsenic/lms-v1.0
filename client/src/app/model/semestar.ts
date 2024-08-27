import {SkolskaGodina} from "./skolskaGodina";

export interface Semestar {
  id: number,
  redniBrojSemestra: number,
  pocetakSemestra: Date,
  krajSemestra: Date,
  skolskaGodina: SkolskaGodina
}
