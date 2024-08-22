import {Ishod} from "./ishod";
import {TipNastave} from "./tipNastave";
import {NastavniMaterijal} from "./nastavniMaterijal";

export interface TerminNastave {
  id: number | null,
  vremePocetka: Date,
  vremeZavrsetka: Date,
  ishod: Ishod | null,
  tipNastave: TipNastave | null,
  nastavniMaterijal: NastavniMaterijal | null
  event?: any
}
