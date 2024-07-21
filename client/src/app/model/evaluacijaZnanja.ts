import {Ishod} from "./ishod";

export interface EvaluacijaZnanja {
  id: number,
  vremePocetka: Date,
  vremeZavrsetka: Date,
  bodovi: number,
  ishod: Ishod,
  tipEvaluacije: TipEvaluacije
}
