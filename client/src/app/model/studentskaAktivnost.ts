import {Aktivnost} from "./aktivnost";
import {StudentNaGodini} from "./studentNaGodini";
import {Predmet} from "./predmet";

export interface StudentskaAktivnost {
  id: number,
  aktivnost: Aktivnost,
  student: StudentNaGodini,
  predmet: Predmet,
  osvojenoPoena: number,
  datum: Date
}
