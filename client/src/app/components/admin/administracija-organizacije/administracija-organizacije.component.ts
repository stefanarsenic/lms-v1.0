import {Component, OnInit} from '@angular/core';
import {NgForOf, NgIf} from "@angular/common";
import {FakultetDialogComponent} from "./fakultet-dialog/fakultet-dialog.component";
import {UniverzitetDialogComponent} from "./univerzitet-dialog/univerzitet-dialog.component";
import {DialogService, DynamicDialogRef} from "primeng/dynamicdialog";
import {UniverzitetService} from "../../../services/univerzitet.service";
import {ButtonDirective} from "primeng/button";
import {FormsModule} from "@angular/forms";
import {animate, state, style, transition, trigger} from "@angular/animations";
import {ChipsModule} from "primeng/chips";
import {AccordionModule} from "primeng/accordion";

@Component({
  selector: 'app-administracija-organizacije',
  standalone: true,
  imports: [
    NgIf,
    NgForOf,
    ButtonDirective,
    FormsModule,
    ChipsModule,
    AccordionModule
  ],
  templateUrl: './administracija-organizacije.component.html',
  styleUrl: './administracija-organizacije.component.css',
  providers: [DialogService],
  animations: [
    trigger('slideInOut', [
      state('in', style({ height: '*', opacity: 1 })),
      transition(':enter', [
        style({ height: '0px', opacity: 0 }),
        animate('300ms ease-in-out', style({ height: '*', opacity: 1 }))
      ]),
      transition(':leave', [
        style({ height: '*', opacity: 1 }),
        animate('300ms ease-in-out', style({ height: '0px', opacity: 0 }))
      ])
    ])
  ]
})
export class AdministracijaOrganizacijeComponent implements OnInit{
  universities: any[] = [];
  ref: DynamicDialogRef | undefined;

  constructor(private universityService: UniverzitetService, private dialogService: DialogService) {}

  ngOnInit() {
    this.universityService.getAll().subscribe(data => {
      this.universities = data;
    });
  }

  searchTerm: string = '';
  isFacultyVisible: boolean[] = [];

  getFilteredFaculties(university:any) {
    if (!this.searchTerm) {
      return university.fakulteti;
    }

    return university.fakulteti.filter((fakultet:any) =>
      fakultet.naziv.toLowerCase().includes(this.searchTerm.toLowerCase())
    );
  }

  isFacultiesVisible: boolean[] = [];  // Tracks visibility of faculties for each university
  isFacultyDetailsVisible: boolean[][] = [];  // Tracks visibility of individual faculty details

  toggleFaculties(index: number) {
    this.isFacultiesVisible[index] = !this.isFacultiesVisible[index];

    // Initialize the array for faculty details if it doesn't exist
    if (!this.isFacultyDetailsVisible[index]) {
      this.isFacultyDetailsVisible[index] = [];
    }
  }

  toggleFacultyDetails(universityIndex: number, facultyIndex: number) {
    if (!this.isFacultyDetailsVisible[universityIndex]) {
      this.isFacultyDetailsVisible[universityIndex] = [];
    }
    this.isFacultyDetailsVisible[universityIndex][facultyIndex] = !this.isFacultyDetailsVisible[universityIndex][facultyIndex];
  }

  openUniverzitetDialog(univerzitet?: any, index?: number) {
    this.ref = this.dialogService.open(UniverzitetDialogComponent, {
      header: univerzitet ? 'Edit University' : 'Add University',
      width: '50%',
      data: {
        univerzitet: univerzitet || {}
      }
    });

    this.ref.onClose.subscribe((result: any) => {
      if (result) {
        if (index !== undefined) {
          // Update the existing university in the array
          this.universities[index] = result;
        } else {
          // Add a new university to the array
          this.universities.push(result);
        }
      }
    });
  }

  openFakultetDialog(fakultet?: any, univerzitetIndex?: number, fakultetIndex?: number) {
    this.ref = this.dialogService.open(FakultetDialogComponent, {
      header: fakultet ? 'Edit Faculty' : 'Add Faculty',
      width: '50%',
      data: {
        fakultet: fakultet || {}
      }
    });

    this.ref.onClose.subscribe((result: any) => {
      if (result) {
        if (univerzitetIndex !== undefined && fakultetIndex !== undefined) {
          // Update the existing faculty in the university's faculty array
          this.universities[univerzitetIndex].fakulteti[fakultetIndex] = result;
        } else if (univerzitetIndex !== undefined) {
          // Add a new faculty to the university's faculty array
          this.universities[univerzitetIndex].fakulteti.push(result);
        }
      }
    });
  }





  deleteUniverzitet(index: number) {
    //this.universityService.deleteUniverzitet(index);
  }

  deleteFakultet(index: number,index2:number) {
    //this.universityService.deleteUniverzitet(index);
  }
}
