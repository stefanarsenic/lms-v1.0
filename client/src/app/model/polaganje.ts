import {Student} from "./student";
import {EvaluacijaZnanja} from "./evaluacijaZnanja";
import {TipPolaganja} from "./tipPolaganja";

export interface Polaganje {
  id: number,
  bodovi: number | null,
  napomena: string | null,
  student: Student,
  tipPolaganja?: TipPolaganja,
  evaluacijaZnanja: EvaluacijaZnanja
}
