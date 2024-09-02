import {Student} from "./student";
import {EvaluacijaZnanja} from "./evaluacijaZnanja";

export interface Polaganje {
  id: number,
  bodovi: number | null,
  napomena: string | null,
  student: Student,
  evaluacijaZnanja: EvaluacijaZnanja
}
