import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DynamicDialogConfig, DynamicDialogRef} from "primeng/dynamicdialog";
import {ChipsModule} from "primeng/chips";
import {ButtonDirective} from "primeng/button";
import {NgIf} from "@angular/common";
import {Drzava} from "../../../../model/drzava";
import {Mesto} from "../../../../model/mesto";
import {Nastavnik} from "../../../../model/nastavnik";
import {NastavnikService} from "../../../../services/nastavnik.service";
import {Fakultet} from "../../../../model/fakultet";
import {DrzavaService} from "../../../../services/drzava.service";
import {DropdownModule} from "primeng/dropdown";

@Component({
  selector: 'app-fakultet-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ChipsModule,
    ButtonDirective,
    NgIf,
    DropdownModule
  ],
  templateUrl: './fakultet-dialog.component.html',
  styleUrl: './fakultet-dialog.component.css'
})
export class FakultetDialogComponent implements OnInit{
  fakultetForm!: FormGroup;
  drzave: Drzava[] = [];
  mesta: Mesto[] = [];
  nastavnici: Nastavnik[] = [];

  constructor(
    private fb: FormBuilder,
    public ref: DynamicDialogRef,
    public config: DynamicDialogConfig,
    private drzavaService: DrzavaService,
    private nastavniciService: NastavnikService
  ) {}

  ngOnInit() {
    const fakultet: Fakultet = this.config.data.fakultet || {};

    this.fakultetForm = this.fb.group({
      naziv: [fakultet.naziv || '', Validators.required],
      adresa: this.fb.group({
        ulica: [fakultet.adresa?.ulica || '', Validators.required],
        broj: [fakultet.adresa?.broj || '', Validators.required],
        drzava: [null, Validators.required],
        mesto: [null, Validators.required]
      }),
      nastavnik: [fakultet.dekan || null, Validators.required]  // Initialize nastavnik properly
    });

    this.loadDrzave();
    this.loadNastavnici();

    this.fakultetForm.get('adresa.drzava')?.valueChanges.subscribe((selectedDrzava) => {
      this.filterMestaByDrzava(selectedDrzava);
      this.fakultetForm.get('adresa.mesto')?.reset();
    });
  }

  loadDrzave() {
    this.drzavaService.getAll().subscribe(r => {
      this.drzave = r;

      const selectedDrzava = this.config.data.fakultet?.adresa?.mesto?.drzava;
      if (selectedDrzava) {
        const drzavaInList = this.drzave.find(d => d.id === selectedDrzava.id);
        this.fakultetForm.get('adresa.drzava')?.setValue(drzavaInList || null);
        if (drzavaInList) {
          this.filterMestaByDrzava(drzavaInList);

          const selectedMesto = this.config.data.fakultet?.adresa?.mesto;
          if (selectedMesto) {
            const mestoInList = this.mesta.find(m => m.id === selectedMesto.id);
            this.fakultetForm.get('adresa.mesto')?.setValue(mestoInList || null);
          }
        }
      }
    });
  }

  filterMestaByDrzava(drzava: Drzava) {
    if (drzava && drzava.mesta) {
      this.mesta = drzava.mesta;
    } else {
      this.mesta = [];
    }
  }

  save() {
    if (this.fakultetForm.valid) {
      const fakultetId = this.config.data.fakultet?.id;
      const formData = this.fakultetForm.value;

      const result = {
        id: fakultetId,
        ...formData
      };

      this.ref.close(result);
    }

  }

  cancel() {
    this.ref.close();
  }

  loadNastavnici() {
    this.nastavniciService.getAll().subscribe(r => {
      this.nastavnici = r;

      const selectedNastavnik = this.config.data.fakultet?.nastavnik;
      if (selectedNastavnik) {
        const nastavnikInList = this.nastavnici.find(n => n.id === selectedNastavnik.id);
        this.fakultetForm.get('nastavnik')?.setValue(nastavnikInList || null);
      }
    });
  }
}
