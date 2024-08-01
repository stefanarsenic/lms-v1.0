import {Adresa} from "./adresa";
import {GodinaStudija} from "./godinaStudija";
import {RegistrovaniKorisnik} from "./registrovaniKorisnik";

export interface Student extends RegistrovaniKorisnik{
  id: number|null,
  adresa: Adresa,
  jmbg: string,
  datumRodjenja: Date|null
}
