import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {ChipsModule} from "primeng/chips";
import {ButtonDirective} from "primeng/button";
import {NgIf} from "@angular/common";
import {Univerzitet} from "../../../../model/univerzitet";
import {DrzavaService} from "../../../../services/drzava.service";
import {Drzava} from "../../../../model/drzava";
import {Mesto} from "../../../../model/mesto";
import {DropdownModule} from "primeng/dropdown";
import {Nastavnik} from "../../../../model/nastavnik";
import {NastavnikService} from "../../../../services/nastavnik.service";
import {formatDateFromString} from "../../../../utils/date-converter";

@Component({
  selector: 'app-univerzitet-dialog',
  standalone: true,
  imports: [
    ChipsModule,
    ReactiveFormsModule,
    ButtonDirective,
    NgIf,
    DropdownModule
  ],
  templateUrl: './univerzitet-dialog.component.html',
  styleUrl: './univerzitet-dialog.component.css'
})
export class UniverzitetDialogComponent implements OnInit {

  univerzitetForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private drzavaSerce: DrzavaService,
    private nastavniciService: NastavnikService
  ) {
  }

  drzave: Drzava[] = []
  mesta: Mesto[] = []
  nastavnici: Nastavnik[] = []

  ngOnInit() {
    const univerzitet: Univerzitet = this.config.data.univerzitet || {};
    this.univerzitetForm = this.fb.group({
      naziv: [univerzitet.naziv || '', Validators.required],
      adresa: this.fb.group({
        ulica: [univerzitet.adresa?.ulica || '', Validators.required],
        broj: [univerzitet.adresa?.broj || '', Validators.required],
        drzava: [null, Validators.required],  // Initially set to null, we'll set it after loading drzave
        mesto: [null, Validators.required]     // Initially set to null, we'll set it after loading mesta
      }),
      rektor: [univerzitet.rektor || null, Validators.required]  // Add rektor field for the dropdown
    });

    // Load Drzave data (from a service, for example)
    this.loadDrzave();
    this.loadNastavnici();
    // Watch for changes in the Drzava dropdown to update the Mesto options
    this.univerzitetForm.get('adresa.drzava')?.valueChanges.subscribe((selectedDrzava) => {
      this.filterMestaByDrzava(selectedDrzava);
      this.univerzitetForm.get('adresa.mesto')?.reset(); // Reset the Mesto selection when Drzava changes
    });

  }


  loadDrzave() {
    this.drzavaSerce.getAll().subscribe(r => {
      this.drzave = r;
      const selectedDrzava = this.config.data.univerzitet?.adresa?.mesto?.drzava;
      if (selectedDrzava) {
        // Find the exact reference to the drzava object in the list
        const drzavaInList = this.drzave.find(d => d.id === selectedDrzava.id);
        this.univerzitetForm.get('adresa.drzava')?.setValue(drzavaInList || null);
        if (drzavaInList) {
          this.filterMestaByDrzava(drzavaInList);

          // Now handle the Mesto after Drzava is set
          const selectedMesto = this.config.data.univerzitet?.adresa?.mesto;
          if (selectedMesto) {
            const mestoInList = this.mesta.find(m => m.id === selectedMesto.id);
            this.univerzitetForm.get('adresa.mesto')?.setValue(mestoInList || null);

          }
        }
      }
    })
  }


  filterMestaByDrzava(drzava: Drzava) {
    if (drzava && drzava.mesta) {
      this.mesta = drzava.mesta; // Use the Mesta list directly from the Drzava object
    } else {
      this.mesta = [];
    }
  }

  save() {
    if (this.univerzitetForm.valid) {
      // Get the original univerzitet ID
      const univerzitetId = this.config.data.univerzitet?.id;
      const datum=formatDateFromString(this.config.data.univerzitet?.datumOsnivanja.toString());
      // Get the form value
      const formData = this.univerzitetForm.value;

      // Include the ID in the data being sent back
      const result = {
        datum:datum,
        id: univerzitetId,
        ...formData
      };

      // Close the dialog and send the result back
      this.ref.close(result);
    }
  }

  cancel() {
    this.ref.close();
  }

  loadNastavnici() {
    this.nastavniciService.getAll().subscribe(r => {
      this.nastavnici = r;

      // Set the initial value for rektor if provided
      const selectedRektor = this.config.data.univerzitet?.rektor;
      if (selectedRektor) {
        const rektorInList = this.nastavnici.find(n => n.id === selectedRektor.id);
        this.univerzitetForm.get('rektor')?.setValue(rektorInList || null);
      }
    });
  }
}

