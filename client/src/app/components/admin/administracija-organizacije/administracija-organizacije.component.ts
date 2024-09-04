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
import {Univerzitet} from "../../../model/univerzitet";
import {AdresaService} from "../../../services/adresa.service";
import {FakultetService} from "../../../services/fakultet.service";

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
  universities: Univerzitet[] = [];
  ref: DynamicDialogRef | undefined;

  constructor(private universityService: UniverzitetService, private dialogService: DialogService,private adresa:AdresaService,
              private univerzitetService: UniverzitetService,private fakultetService:FakultetService) {}

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
        // Check if address has changed
        const originalAddress = univerzitet?.adresa;
        const newAddress = result.adresa;
        const datum=result.datum
        const addressChanged = this.hasAddressChanged(originalAddress, newAddress);
        result.datumOsnivanja=datum
        if (index !== undefined) {
          if (addressChanged) {
            this.adresa.create(newAddress).subscribe(r => {
              result.adresa = r;
              this.univerzitetService.update(result.id, result).subscribe(r => {
                console.log(r);
              });
            });
          } else {
            // Update without changing the address
            this.univerzitetService.update(result.id, result).subscribe(r => {
              console.log(r);
            });
          }
          this.universities[index] = result;
        } else {
          // Add a new university to the array
          this.universities.push(result);
        }
      }
    });
  }

  hasAddressChanged(originalAddress: any, newAddress: any): boolean {
    if (!originalAddress || !newAddress) return true;

    return (
      originalAddress.ulica !== newAddress.ulica ||
      originalAddress.broj !== newAddress.broj ||
      originalAddress.mesto?.id !== newAddress.mesto?.id ||
      originalAddress.mesto?.drzava?.id !== newAddress.mesto?.drzava?.id
    );
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
        const originalAddress = fakultet?.adresa;
        const newAddress = result.adresa;
        const addressChanged = this.hasAddressChanged(originalAddress, newAddress);

        if (univerzitetIndex !== undefined && fakultetIndex !== undefined) {
          result.univerzitet = fakultet.univerzitet;
          result.studijskiProgrami = fakultet.studijskiProgrami;
          if (addressChanged) {
            this.adresa.create(newAddress).subscribe(r => {
              result.adresa = r;
              this.fakultetService.update(result.id, result).subscribe(r => {
                this.universities[univerzitetIndex].fakulteti[fakultetIndex] = result;
              });
            });
          } else {
            this.fakultetService.update(result.id, result).subscribe(r => {
              this.universities[univerzitetIndex].fakulteti[fakultetIndex] = result;
            });
          }
        } else if (univerzitetIndex !== undefined) {
          result.univerzitet = this.universities[univerzitetIndex];
          result.univerzitet.fakulteti = result.univerzitet.fakulteti || [];
          result.studijskiProgrami = [];
          if (addressChanged) {
            this.adresa.create(newAddress).subscribe(r => {
              result.adresa = r;
              this.fakultetService.create(result).subscribe(r => {
                this.universities[univerzitetIndex].fakulteti.push(result);
                console.log(this.universities[univerzitetIndex].fakulteti)
                this.isFacultiesVisible[univerzitetIndex] = true; // Ensure faculties list is visible
              });
            });
          } else {
            this.fakultetService.create(result).subscribe(r => {
              this.universities[univerzitetIndex].fakulteti.push(result);
              this.isFacultiesVisible[univerzitetIndex] = true; // Ensure faculties list is visible
            });
          }
        }
      }
    });
  }





  deleteUniverzitet(index: number) {
    this.univerzitetService.delete(index).subscribe(r=>{
      console.log(r)
    })
  }

  deleteFakultet(univerzitetIndex: number, fakultetIndex: number) {
    const fakultetId = this.universities[univerzitetIndex].fakulteti[fakultetIndex].id;
    this.fakultetService.delete(fakultetId).subscribe(() => {
      this.universities[univerzitetIndex].fakulteti.splice(fakultetIndex, 1);
    });
  }

}
