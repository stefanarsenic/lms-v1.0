import { Component } from '@angular/core';
import {RouterLink} from "@angular/router";
import {ButtonDirective} from "primeng/button";
import {Ripple} from "primeng/ripple";
import {NgOptimizedImage} from "@angular/common";

@Component({
  standalone: true,
  selector: 'app-access',
  templateUrl: './access.component.html',

  imports: [
    RouterLink,
    ButtonDirective,
    Ripple,
    NgOptimizedImage
  ]
})
export class AccessComponent { }
