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

  form: FormGroup;
  selectedOption: string = 'password';

  options: any[] = [
    {label: 'Change Password', value: 'password', icon: 'pi pi-lock'},
    {label: 'Change Email', value: 'email', icon: 'pi pi-envelope'}
  ];

  constructor(private fb: FormBuilder, private messageService: MessageService) {
    this.form = this.fb.group({
      currentPassword: ['', Validators.required],
      newPassword: ['', [Validators.required, Validators.minLength(8)]],
      confirmPassword: ['', Validators.required],
      email: ['', [Validators.required, Validators.email]]
    })
  }
  ngOnInit() {

  }


  passwordMatchValidator(group: FormGroup) {
    const password = group.get('newPassword')?.value;
    const confirmPassword = group.get('confirmPassword')?.value;
    return password === confirmPassword ? null : { mismatch: true };
  }

  onSubmitChangePassword() {
    if (this.form.valid) {
      this.messageService.add({severity: 'success', summary: 'Success', detail: 'Password changed successfully!'});
      // Perform your password change logic here
    } else {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Please correct the errors in the form!'});
    }
  }

  onSubmitChangeEmail() {
    if (this.form.get('email')?.valid) {
      this.messageService.add({severity: 'success', summary: 'Success', detail: 'Email changed successfully!'});
      // Perform your email change logic here
    } else {
      this.messageService.add({severity: 'error', summary: 'Error', detail: 'Please enter a valid email!'});
    }
  }
}
