import { Routes } from '@angular/router';
import {HomePageComponent} from "./components/home-page/home-page.component";
import {LoginComponent} from "./components/login/login.component";
import {authGuard} from "./guards/auth.guard";
import {UserProfileComponent} from "./components/user/user-profile/user-profile.component";
import {RegisterComponent} from "./components/user/register/register.component";
import {MainStudentComponent} from "./components/student/main-student/main-student.component";
import {FakultetComponent} from "./components/home-page/fakultet/fakultet.component";
import {StudijskiProgramComponent} from "./components/home-page/studijski-program/studijski-program.component";
import {AdminProfileComponent} from "./components/admin/admin-profile/admin-profile.component";
import {OUniverzitetuComponent} from "./components/home-page/o-univerzitetu/o-univerzitetu.component";
import {StudijskiProgramCrudComponent} from "./components/admin/studijski-program/studijski-program-crud/studijski-program-crud.component";
import {UsersComponent} from "./components/admin/users/users.component";
import {StudentskaSluzbaComponent} from "./components/studentska-sluzba/studentska-sluzba.component";
import {DokumentaComponent} from "./components/studentska-sluzba/dokumenta/dokumenta.component";
import {UverenjeORedovnomStudiranjuComponent} from "./components/studentska-sluzba/dokumenta/uverenje-o-redovnom-studiranju/uverenje-o-redovnom-studiranju.component";
import {
  UverenjeOPolozenimIspitimaComponent
} from "./components/studentska-sluzba/dokumenta/uverenje-o-polozenim-ispitima/uverenje-o-polozenim-ispitima.component";
import {
  PotvrdaOPrijaviRasporedaIspitaComponent
} from "./components/studentska-sluzba/dokumenta/potvrda-o-prijavi-rasporeda-ispita/potvrda-o-prijavi-rasporeda-ispita.component";
import {
  UverenjeOProsecnojOceniComponent
} from "./components/studentska-sluzba/dokumenta/uverenje-o-prosecnoj-oceni/uverenje-o-prosecnoj-oceni.component";
import {
  UverenjeOZavrsetkuStudijaComponent
} from "./components/studentska-sluzba/dokumenta/uverenje-o-zavrsetku-studija/uverenje-o-zavrsetku-studija.component";
import {
  PriznanjeIspitaComponent
} from "./components/studentska-sluzba/dokumenta/priznanje-ispita/priznanje-ispita.component";
import {NastavnikComponent} from "./components/admin/nastavnik/nastavnik.component";
import {UpisStudenataComponent} from "./components/studentska-sluzba/upis-studenata/upis-studenata.component";
import {StudentiComponent} from "./components/admin/studenti/studenti.component";
import {UlogeComponent} from "./components/admin/uloge/uloge.component";
import {ObavestenjaComponent} from "./components/studentska-sluzba/obavestenja/obavestenja.component";
import {NastavaComponent} from "./components/studentska-sluzba/rasporedi/raspored-nastave/nastava.component";
import {NastavnikProfileComponent} from "./components/nastavnik/nastavnik/nastavnik-profile.component";
import {SpisakPredmetaComponent} from "./components/nastavnik/spisak-predmeta/spisak-predmeta.component";
import {SpisakStudenataComponent} from "./components/nastavnik/spisak-studenata/spisak-studenata.component";
import {
  OrganizacijaStudijskogProgramaComponent
} from "./components/admin/organizacija-studijskog-programa/organizacija-studijskog-programa.component";
import {PretragaStudenataComponent} from "./components/nastavnik/pretraga-studenata/pretraga-studenata.component";
import {UredjivanjeSilabusaComponent} from "./components/nastavnik/uredjivanje-silabusa/uredjivanje-silabusa.component";
import {
  UpravljanjeObavestenjimaComponent
} from "./components/nastavnik/upravljanje-obavestenjima/upravljanje-obavestenjima.component";
import {PregledPredmetaComponent} from "./components/student/pregled-predmeta/pregled-predmeta.component";
import {PregledObavestenjaComponent} from "./components/student/pregled-obavestenja/pregled-obavestenja.component";
import {DashboardComponent} from "./components/student/dashboard/dashboard.component";
import {EditProfilComponent} from "./components/student/student-edit-profil/edit-profil.component";
import {IspitiComponent} from "./components/studentska-sluzba/rasporedi/raspored-ispita/ispiti.component";
import {PrijavaIspitaComponent} from "./components/student/prijava-ispita/prijava-ispita.component";
import {
  RasporedEvaluacijaZnanjaComponent
} from "./components/studentska-sluzba/rasporedi/raspored-evaluacija-znanja/raspored-evaluacija-znanja.component";
import {IstorijaStudiranjaComponent} from "./components/student/istorija-studiranja/istorija-studiranja.component";
import {RasporedIshodaComponent} from "./components/nastavnik/raspored-ishoda/raspored-ishoda.component";
import {PredmetiComponent} from "./components/admin/predmeti/predmeti.component";
import {AccessComponent} from "./components/access/access.component";
import {NotFoundComponent} from "./components/not-found/not-found.component";
import {
  AdministracijaOrganizacijeComponent
} from "./components/admin/administracija-organizacije/administracija-organizacije.component";
import {ZahtevMaterijalaComponent} from "./components/studentska-sluzba/zahtev-materijala/zahtev-materijala.component";
import {
  AdministracijaZahetvaComponent
} from "./components/admin/administracija-zahetva/administracija-zahetva.component";
import {
  SpisakStudenataNastavnikComponent
} from "./components/nastavnik/spisak-studenata-nastavnik/spisak-studenata-nastavnik.component";
import {UnosOcenaComponent} from "./components/nastavnik/unos-ocena/unos-ocena.component";
import {
  InstrumentiEvaluacijeComponent
} from "./components/nastavnik/instrumenti-evaluacije/instrumenti-evaluacije.component";

StudijskiProgramCrudComponent

//TODO: guardovi
export const routes: Routes = [
  {
    path: "",
    component: HomePageComponent,
    children:[
      {
        path: "login",
        component: LoginComponent
      },
      {
        path: "o-univerzitetu",
        component: OUniverzitetuComponent
      },
      {
        path: "fakultet",
        component: FakultetComponent
      },
      {
        path: "fakultet/:id",
        component: FakultetComponent
      },
      {
        path: "studijski-program",
        component: StudijskiProgramComponent
      },
      {
        path: "studijski-program/:id",
        component: StudijskiProgramComponent
      }
    ]
  },
  {
    path: "studentska-sluzba",
    component: StudentskaSluzbaComponent,
    canActivate: [authGuard],
    data: {
      allowedRoles: ["ROLE_SLUZBA"]
    },
    children: [
      {
        path: "edit-profil",
        component: EditProfilComponent
      },
      {
        path: "upis-studenata",
        component: UpisStudenataComponent
      },
      {
        path: "obavestenja",
        component: ObavestenjaComponent
      },
      {
        path: "dokumenta",
        component: DokumentaComponent,
        children: [
          {
            path: "uverenje-o-redovnom-studiranju",
            component: UverenjeORedovnomStudiranjuComponent
          },
          {
            path: "uverenje-o-polozenim-ispitima",
            component: UverenjeOPolozenimIspitimaComponent
          },
          {
            path: "potvrda-o-prijavi-rasporeda-ispita",
            component: PotvrdaOPrijaviRasporedaIspitaComponent
          },
          {
            path: "uverenje-o-prosecnoj-oceni",
            component: UverenjeOProsecnojOceniComponent
          },
          {
            path: "uverenje-o-zavrsetku-studija",
            component: UverenjeOZavrsetkuStudijaComponent
          },
          {
            path: "priznanje-ispita",
            component: PriznanjeIspitaComponent
          },
        ]
      },
      {
        path: "raspored-nastave",
        component: NastavaComponent
      },
      {
        path: "raspored-ispita",
        component: IspitiComponent
      },
      {
        path: "raspored-evaluacija-znanja",
        component: RasporedEvaluacijaZnanjaComponent
      },
      {
        path: "zahtev-materijala",
        component: ZahtevMaterijalaComponent
      }
    ]
  },
  {
    path: "admin",
    component: AdminProfileComponent,
    children: [
      {
        path: "edit-profil",
        component: EditProfilComponent
      },
      {
        path: "studijski-programi",
        component: StudijskiProgramCrudComponent
      },
      {
        path: "korisnici",
        component: UsersComponent
      },
      {
        path: "nastavnici",
        component: NastavnikComponent
      },
      {
        path: "studenti",
        component: StudentiComponent
      },
      {
        path: "uloge",
        component: UlogeComponent
      },
      {
        path: "organizacija-studijskog-programa",
        component: OrganizacijaStudijskogProgramaComponent
      },
      {
        path: "predmeti",
        component: PredmetiComponent
      },
      {
        path: "administracija-organizacije",
        component: AdministracijaOrganizacijeComponent
      },
      {
        path: "administracija-zahteva",
        component:AdministracijaZahetvaComponent
      }
    ],
    canActivate:[authGuard],data:{
      allowedRoles:["ROLE_ADMIN"]
    }
  },
  {
    path:"korisnik",
    component: UserProfileComponent,
    canActivate:[authGuard],data:{
      allowedRoles:["ROLE_REGULAR"]
    }
  },
  {
    path:"nastavnik",
    component: NastavnikProfileComponent,
    children:[
      {
        path: "edit-profil",
        component: EditProfilComponent
      },
      {
        path: "instrumenti-evaluacije",
        component: InstrumentiEvaluacijeComponent,
      },
      {
        path: "informacije-studenata",
        component: SpisakStudenataNastavnikComponent,
      },
      {
        path: "spisak-predmeta",
        component: SpisakPredmetaComponent
      },
      {
        path: "studenti-spisak",
        component: SpisakStudenataComponent
      },
      {
        path: "pretraga-studenata",
        component: PretragaStudenataComponent
      },
      {
        path: "uredjivanje-silabusa",
        component: UredjivanjeSilabusaComponent
      },
      {
        path: "uredjivanje-obavestenja",
        component: UpravljanjeObavestenjimaComponent
      },
      {
        path: "raspored-ishoda",
        component: RasporedIshodaComponent
      },
      {
        path: "unos-ocena",
        component: UnosOcenaComponent
      }
    ],
    canActivate:[authGuard],data:{
      allowedRoles:["ROLE_NASTAVNIK"]
    }
  },
  {
    path:"student",
    component:MainStudentComponent,
    children:[
      {
        path: "dashboard",
        component: DashboardComponent
      },
      {
        path: "pregled-predmeta",
        component: PregledPredmetaComponent
      },
      {
        path: "pregled-obavestenja",
        component: PregledObavestenjaComponent
      },
      {
        path: "edit-profil",
        component: EditProfilComponent
      },
      {
        path: "prijava-ispita",
        component: PrijavaIspitaComponent
      },
      {
        path: "istorija-studiranja",
        component: IstorijaStudiranjaComponent
      }
    ],
    canActivate:[authGuard],data:{
      allowedRoles:["ROLE_STUDENT"]
    }
  },

  {
    path:"register",
    component: RegisterComponent
    // canActivate:[authGuard],data:{
    //   allowedRoles:["ROLE_REGULAR"]
    // }
  },
  {
    path:"access-denied",
    component: AccessComponent
  },
  { path: '**', component: NotFoundComponent },
];
