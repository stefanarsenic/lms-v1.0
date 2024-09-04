import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router, RouterLink, RouterOutlet} from "@angular/router";
import {PredmetService} from "../../../../services/predmet.service";
import {Predmet} from "../../../../model/predmet";
import {ObavestenjaService} from "../../../../services/obavestenja.service";
import {HttpParams} from "@angular/common/http";
import {Obavestenje} from "../../../../model/obavestenje";
import {NgForOf} from "@angular/common";

@Component({
  selector: 'app-dinamicki-prikaz-predmeta',
  standalone: true,
  imports: [
    NgForOf,
    RouterOutlet,
    RouterLink
  ],
  templateUrl: './dinamicki-prikaz-predmeta.component.html',
  styleUrl: './dinamicki-prikaz-predmeta.component.css'
})
export class DinamickiPrikazPredmetaComponent implements OnInit{

  predmet!: Predmet;

  obavestenja: Obavestenje[] = [];

  constructor(
    private route: ActivatedRoute,
    private predmetService: PredmetService,
    private obavestenjaService: ObavestenjaService
  ) {
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      const id: number = Number(this.route.snapshot.paramMap.get('id'));
      this.predmetService.getById(id).subscribe((data: Predmet) => {
        this.predmet = data;
        let params: HttpParams = new HttpParams()
          .set("predmetId", data.id)
        this.obavestenjaService.getByPredmet(params).subscribe(data => this.obavestenja = data);
      });
    });
  }

}
