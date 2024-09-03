import { NgModule } from '@angular/core';
import {CommonModule, NgOptimizedImage} from '@angular/common';
import { ButtonModule } from 'primeng/button';

import { AccessRoutingModule } from './access-routing.module';
import { AccessComponent } from './access.component';
import {Ripple} from "primeng/ripple";

@NgModule({
  imports: [
    CommonModule,
    AccessRoutingModule,
    ButtonModule,
    Ripple,
    NgOptimizedImage
  ],
    declarations: [AccessComponent]
})
export class AccessModule { }
