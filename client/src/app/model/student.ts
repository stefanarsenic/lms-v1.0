import {Adresa} from "./adresa";
import {GodinaStudija} from "./godinaStudija";
import {RegistrovaniKorisnik} from "./registrovaniKorisnik";

export interface Student extends RegistrovaniKorisnik{
  id: number,
  adresa: Adresa,
  godinaStudija: GodinaStudija
}
