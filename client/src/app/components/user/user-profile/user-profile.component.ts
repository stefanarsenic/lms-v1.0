import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {KorisnikService} from "../../../services/korisnik.service";

@Component({
  selector: 'app-user-profile',
  standalone: true,
  imports: [
    ReactiveFormsModule,
    NgIf
  ],
  templateUrl: './user-profile.component.html',
  styleUrl: './user-profile.component.css'
})
export class UserProfileComponent {

  profileForm!: FormGroup;
  username="test"
  constructor(private fb: FormBuilder,private korisnikService:KorisnikService) {}

  ngOnInit(): void {
    this.profileForm = this.fb.group({
      ime: ['', Validators.required],
      prezime: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(6)]]
    });
    let token=localStorage.getItem("token")
    if(token){
      this.username=JSON.parse(atob(token.split(".")[1]))["username"]
      this.korisnikService.getUserInfo(this.username).subscribe(r=>{
        this.profileForm.patchValue(r)
      })
    }


  }

  onSubmit(): void {
    if (this.profileForm.valid) {
      console.log(this.profileForm.value);
      // Handle form submission, e.g., send data to the server
    }
  }

}
