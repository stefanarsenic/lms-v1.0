import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UverenjeORedovnomStudiranjuComponent } from './uverenje-o-redovnom-studiranju.component';

describe('UverenjeORedovnomStudiranjuComponent', () => {
  let component: UverenjeORedovnomStudiranjuComponent;
  let fixture: ComponentFixture<UverenjeORedovnomStudiranjuComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UverenjeORedovnomStudiranjuComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UverenjeORedovnomStudiranjuComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
