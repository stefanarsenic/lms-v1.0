import {RegistrovaniKorisnik} from "./registrovaniKorisnik";

export interface ZahtevMaterijala{
  id:number|null,
  naslov:string,
  opis:string,
  korisnik:RegistrovaniKorisnik,
  datumPodnosenja:Date,
  status:string,
  admin:RegistrovaniKorisnik,
  datumIzmena:Date
}
