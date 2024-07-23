import {RegistrovaniKorisnik} from "./registrovaniKorisnik";
import {Uloga} from "./uloga";
export interface PravoPristupa {
  id: number|undefined,
  uloga: Uloga|undefined,
  registrovaniKorisnik: RegistrovaniKorisnik|null
}
