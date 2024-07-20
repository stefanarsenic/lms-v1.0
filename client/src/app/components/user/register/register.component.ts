import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf, NgSwitch, NgSwitchCase} from "@angular/common";
import {KorisnikService} from "../../../services/korisnik.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf,
    NgSwitch,
    NgSwitchCase
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  registerForm!: FormGroup;
  currentStep = 1;

  constructor(private fb: FormBuilder,private korisnikService:KorisnikService,private router:Router) {}

  ngOnInit(): void {
    this.registerForm = this.fb.group({
      ime: ['', Validators.required],
      prezime: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      korisnickoIme: ['', Validators.required],
      lozinka: ['', [Validators.required, Validators.minLength(6)]]
    });
  }

  nextStep(): void {
    if (this.currentStep < 3) {
      this.currentStep++;
    }
  }

  prevStep(): void {
    if (this.currentStep > 1) {
      this.currentStep--;
    }
  }

  onSubmit(): void {
    if (this.registerForm.valid) {
      this.korisnikService.register(this.registerForm.value).subscribe(r=>{
        this.router.navigate(["/login"])
      })
    }
  }
}
