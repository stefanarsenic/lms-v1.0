import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UverenjeOPolozenimIspitimaComponent } from './uverenje-o-polozenim-ispitima.component';

describe('UverenjeOPolozenimIspitimaComponent', () => {
  let component: UverenjeOPolozenimIspitimaComponent;
  let fixture: ComponentFixture<UverenjeOPolozenimIspitimaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UverenjeOPolozenimIspitimaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UverenjeOPolozenimIspitimaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
