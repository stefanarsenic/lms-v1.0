import {RegistrovaniKorisnik} from "./registrovaniKorisnik";

export interface PravoPristupa {
  id: number,
  uloga: Uloga,
  registrovaniKorisnik: RegistrovaniKorisnik
}
