import {Time} from "@angular/common";

export interface Ispit {
  id: number,
  datumPolaganja: Date,
  termin: Date,
  sala: string,
  poruka: string,
  ispitniRok: IspitniRok,
  predmet: Predmet
}
