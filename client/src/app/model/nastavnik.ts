import {RegistrovaniKorisnik} from "./registrovaniKorisnik";
import {Zvanje} from "./zvanje";

export interface Nastavnik extends RegistrovaniKorisnik{
  biografija: string,
  jmbg: string,
  zvanja?: Zvanje[]
}
