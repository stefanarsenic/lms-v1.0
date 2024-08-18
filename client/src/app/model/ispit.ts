import {IspitniRok} from "./ispitniRok";
import {Predmet} from "./predmet";
import {StudijskiProgram} from "./studijskiProgram";

export interface Ispit {
  id: number,
  pocetakIspita: Date,
  krajIspita: Date,
  ispitniRok: IspitniRok,
  predmet: Predmet
  studijskiProgram: StudijskiProgram
}
