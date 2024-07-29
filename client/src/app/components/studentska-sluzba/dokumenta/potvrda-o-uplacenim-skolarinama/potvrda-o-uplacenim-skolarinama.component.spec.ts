import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PotvrdaOUplacenimSkolarinamaComponent } from './potvrda-o-uplacenim-skolarinama.component';

describe('PotvrdaOUplacenimSkolarinamaComponent', () => {
  let component: PotvrdaOUplacenimSkolarinamaComponent;
  let fixture: ComponentFixture<PotvrdaOUplacenimSkolarinamaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PotvrdaOUplacenimSkolarinamaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PotvrdaOUplacenimSkolarinamaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
