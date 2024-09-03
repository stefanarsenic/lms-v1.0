import {Component, Input} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {DynamicDialogRef} from "primeng/dynamicdialog";
import {ChipsModule} from "primeng/chips";
import {ButtonDirective} from "primeng/button";
import {NgIf} from "@angular/common";

@Component({
  selector: 'app-fakultet-dialog',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    ChipsModule,
    ButtonDirective,
    NgIf
  ],
  templateUrl: './fakultet-dialog.component.html',
  styleUrl: './fakultet-dialog.component.css'
})
export class FakultetDialogComponent {
  @Input() fakultet: any;  // Input property to receive the faculty data

  fakultetForm!: FormGroup;

  constructor(private fb: FormBuilder, public ref: DynamicDialogRef) {}

  ngOnInit() {
    this.fakultetForm = this.fb.group({
      naziv: [this.fakultet?.naziv || '', Validators.required],
      adresa: this.fb.group({
        ulica: [this.fakultet?.adresa?.ulica || '', Validators.required],
        broj: [this.fakultet?.adresa?.broj || '', Validators.required],
      }),
    });
  }

  save() {
    if (this.fakultetForm.valid) {
      this.ref.close(this.fakultetForm.value);
    }
  }

  cancel() {
    this.ref.close();
  }
}
