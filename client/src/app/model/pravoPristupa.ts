import {RegistrovaniKorisnik} from "./registrovaniKorisnik";
import {Uloga} from "./uloga";

export interface PravoPristupa {
  id: number,
  uloga: Uloga,
  registrovaniKorisnik: RegistrovaniKorisnik
}
