import {Ishod} from "./ishod";
import {TipEvaluacije} from "./tipEvaluacije";

export interface EvaluacijaZnanja {
  id: number,
  vremePocetka: Date,
  vremeZavrsetka: Date,
  bodovi: number,
  ishod: Ishod,
  tipEvaluacije: TipEvaluacije
}
