import {Ispit} from "./ispit";

export interface PolozeniIspiti {
  id: number,
  ocena: number,
  bodovi: number,
  student: Student,
  ispit: Ispit
}
