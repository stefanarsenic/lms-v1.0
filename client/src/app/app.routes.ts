import { Routes } from '@angular/router';
import {HomePageComponent} from "./components/home-page/home-page.component";
import {LoginComponent} from "./components/login/login.component";
import {authGuard} from "./guards/auth.guard";
import {UserProfileComponent} from "./components/user/user-profile/user-profile.component";
import {RegisterComponent} from "./components/user/register/register.component";
import {MainStudentComponent} from "./components/main-student/main-student.component";
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
import {NastavaComponent} from "./components/studentska-sluzba/rasporedi/nastava/nastava.component";
import {NastavnikProfileComponent} from "./components/nastavnik/nastavnik/nastavnik-profile.component";
import {SpisakPredmetaComponent} from "./components/nastavnik/spisak-predmeta/spisak-predmeta.component";
import {SpisakStudenataComponent} from "./components/nastavnik/spisak-studenata/spisak-studenata.component";
import {
  OrganizacijaStudijskogProgramaComponent
} from "./components/admin/organizacija-studijskog-programa/organizacija-studijskog-programa.component";

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
    // canActivate: [authGuard],
    // data: {
    //   allowedRoles: ["ROLE_SLUZBA"]
    // }
    children: [
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
        path: "nastava",
        component: NastavaComponent
      },
    ]
  },
  {
    path: "admin",
    component: AdminProfileComponent,
    children: [
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
      }
    ],
    canActivate:[authGuard],data:{
      allowedRoles:["ROLE_ADMIN"]
    }
  },
  {
    path: "main",
    component: MainStudentComponent,
    canActivate:[authGuard],
    data:{
    allowedRoles:["ROLE_NASTAVNIK","ROLE_ADMIN"]
    }},
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
        path: "spisak-predmeta",
        component: SpisakPredmetaComponent
      },
      {
        path: "studenti-spisak",
        component: SpisakStudenataComponent
      }
    ],
    canActivate:[authGuard],data:{
      allowedRoles:["ROLE_NASTAVNIK"]
    }
  },

  {
    path:"register",
    component: RegisterComponent
    // canActivate:[authGuard],data:{
    //   allowedRoles:["ROLE_REGULAR"]
    // }
  }
];
