import {PravoPristupa} from "./pravoPristupa";

export interface RegistrovaniKorisnik {
  id: number,
  korisnickoIme: string,
  lozinka: string,
  email: string,
  ime: string,
  prezime: string,
  pravoPristupaSet: PravoPristupa[]
}
