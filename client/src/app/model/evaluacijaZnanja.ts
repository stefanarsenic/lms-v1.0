import {Ishod} from "./ishod";
import {TipEvaluacije} from "./tipEvaluacije";
import {RealizacijaPredmeta} from "./realizacijaPredmeta";
import {Fajl} from "./fajl";

export interface EvaluacijaZnanja {
  id: number,
  vremePocetka: Date,
  vremeZavrsetka: Date,
  bodovi: number,
  ishod: Ishod,
  instrumentEvaluacije?: Fajl,
  tipEvaluacije: TipEvaluacije,
  realizacijaPredmeta?: RealizacijaPredmeta
}
