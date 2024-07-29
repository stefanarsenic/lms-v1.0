import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PotvrdaOPrijaviRasporedaIspitaComponent } from './potvrda-o-prijavi-rasporeda-ispita.component';

describe('PotvrdaOPrijaviRasporedaIspitaComponent', () => {
  let component: PotvrdaOPrijaviRasporedaIspitaComponent;
  let fixture: ComponentFixture<PotvrdaOPrijaviRasporedaIspitaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PotvrdaOPrijaviRasporedaIspitaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PotvrdaOPrijaviRasporedaIspitaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
