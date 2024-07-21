import {Ishod} from "./ishod";
import {TipNastave} from "./tipNastave";
import {NastavniMaterijal} from "./nastavniMaterijal";

export interface TerminNastave {
  id: number,
  vremePocetka: Date,
  vremeZavrsetka: Date,
  ishod: Ishod,
  tipNastave: TipNastave,
  nastavniMaterijal: NastavniMaterijal
}
