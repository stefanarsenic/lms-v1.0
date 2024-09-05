import {Component, OnInit} from '@angular/core';
import {InputTextModule} from "primeng/inputtext";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Student} from "../../../model/student";
import {ButtonDirective} from "primeng/button";
import {CalendarModule} from "primeng/calendar";
import {PasswordModule} from "primeng/password";
import { TooltipModule } from 'primeng/tooltip';
import {NgIf} from "@angular/common";
import {RadioButtonModule} from "primeng/radiobutton";
import {CardModule} from "primeng/card";
import {ToggleButtonModule} from "primeng/togglebutton";
import {MessageService} from "primeng/api";
import {ToastModule} from "primeng/toast";
import {SelectButtonModule} from "primeng/selectbutton";
import {MessageModule} from "primeng/message";
import {KorisnikService} from "../../../services/korisnik.service";
@Component({
  selector: 'app-student-edit-profil',
  standalone: true,
  imports: [
    InputTextModule,
    ButtonDirective,
    CalendarModule,
    ReactiveFormsModule,
    PasswordModule,
    TooltipModule,
    FormsModule,
    NgIf,
    RadioButtonModule,
    CardModule,
    ToggleButtonModule,
    ToastModule,
    SelectButtonModule,
    MessageModule
  ],
  templateUrl: './edit-profil.component.html',
  styleUrl: './edit-profil.component.css'
})
export class EditProfilComponent implements OnInit {

  passwordForm!: FormGroup;
  emailForm!: FormGroup;
  selectedOption: string = 'password';
  username:string="";

  options: any[] = [
    { label: 'Change Password', value: 'password', icon: 'pi pi-lock' },
    { label: 'Change Email', value: 'email', icon: 'pi pi-envelope' }
  ];

  constructor(private fb: FormBuilder, private messageService: MessageService, private korisnikService:KorisnikService) {
    // Password form group
    this.passwordForm = this.fb.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required]
    }, { validator: this.passwordMatchValidator });

    // Email form group
    this.emailForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]]
    });
  }

  ngOnInit() {}

  passwordMatchValidator(group: FormGroup) {
    const password = group.get('newPassword')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  onSubmitChangePassword() {
    let token=localStorage.getItem("token")
    if(this.passwordForm.valid){
      if (token) {
        this.username=JSON.parse(atob(token.split(".")[1]))["username"]
        this.korisnikService.changePassword(this.username,this.passwordForm.get("currentPassword")?.value,this.passwordForm.get("newPassword")?.value).subscribe(r=>{
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Password changed successfully!' });
        })
      }
    }
    else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Please correct the errors in the form!' });
    }
  }

  onSubmitChangeEmail() {
    let token=localStorage.getItem("token")
    if(this.emailForm.valid){
      if (token) {
        this.username=JSON.parse(atob(token.split(".")[1]))["username"]
        this.korisnikService.changeEmail(this.username,this.emailForm.get("email")?.value).subscribe(r=>{
          this.messageService.add({ severity: 'success', summary: 'Success', detail: 'Email changed successfully!' });
        })
      }
    }else {
      this.messageService.add({ severity: 'error', summary: 'Error', detail: 'Please enter a valid email!' });
    }
  }
}
