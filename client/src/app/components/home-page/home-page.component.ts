import {Component, OnInit} from '@angular/core';
import {Router, RouterLink, RouterOutlet} from "@angular/router";
import {UniverzitetService} from "../../services/univerzitet.service";
import {Univerzitet} from "../../model/univerzitet";
import {NgForOf} from "@angular/common";
import {FormsModule} from "@angular/forms";

@Component({
  selector: 'app-home-page',
  standalone: true,
  imports: [
    RouterOutlet,
    RouterLink,
    NgForOf,
    FormsModule
  ],
  templateUrl: './home-page.component.html',
  styleUrl: './home-page.component.css'
})
export class HomePageComponent implements OnInit{
  constructor(
    private univerzitetService: UniverzitetService,
    private router: Router
  ) {}

  univerzitet: Univerzitet[] = [];
  selectedFakultetId: number | undefined;

  ngOnInit(): void {
    this.univerzitetService.getAll().subscribe(data => {
      this.univerzitet = data;
      console.log(this.univerzitet);
    })
  }

  onSelectFakultet(event: Event){
    if(this.selectedFakultetId){
      this.router.navigate(['/fakultet', this.selectedFakultetId])
    }
  }
}
