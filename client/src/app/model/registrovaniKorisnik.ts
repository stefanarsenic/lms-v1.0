import {PravoPristupa} from "./pravoPristupa";

export interface RegistrovaniKorisnik {
  id: number|null,
  korisnickoIme: string,
  lozinka: string,
  email: string,
  ime: string,
  prezime: string,
  pravoPristupaSet: PravoPristupa[]
}
