import {Component, Input, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DynamicDialogRef} from "primeng/dynamicdialog";
import {ChipsModule} from "primeng/chips";
import {ButtonDirective} from "primeng/button";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-univerzitet-dialog',
  standalone: true,
  imports: [
    ChipsModule,
    ReactiveFormsModule,
    ButtonDirective,
    NgIf
  ],
  templateUrl: './univerzitet-dialog.component.html',
  styleUrl: './univerzitet-dialog.component.css'
})
export class UniverzitetDialogComponent implements OnInit{

  @Input() univerzitet: any;  // Input property to receive the university data

  univerzitetForm!: FormGroup;

  constructor(private fb: FormBuilder, public ref: DynamicDialogRef) {}

  ngOnInit() {
    this.univerzitetForm = this.fb.group({
      naziv: [this.univerzitet?.naziv || '', Validators.required],
      datumOsnivanja: [this.univerzitet?.datumOsnivanja || '', Validators.required],
      adresa: this.fb.group({
        ulica: [this.univerzitet?.adresa?.ulica || '', Validators.required],
        broj: [this.univerzitet?.adresa?.broj || '', Validators.required],
      }),
    });
  }

  save() {
    if (this.univerzitetForm.valid) {
      this.ref.close(this.univerzitetForm.value);
    }
  }

  cancel() {
    this.ref.close();
  }

}
