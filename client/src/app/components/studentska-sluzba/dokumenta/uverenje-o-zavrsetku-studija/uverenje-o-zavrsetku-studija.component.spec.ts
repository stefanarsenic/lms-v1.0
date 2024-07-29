import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UverenjeOZavrsetkuStudijaComponent } from './uverenje-o-zavrsetku-studija.component';

describe('UverenjeOZavrsetkuStudijaComponent', () => {
  let component: UverenjeOZavrsetkuStudijaComponent;
  let fixture: ComponentFixture<UverenjeOZavrsetkuStudijaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UverenjeOZavrsetkuStudijaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UverenjeOZavrsetkuStudijaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
