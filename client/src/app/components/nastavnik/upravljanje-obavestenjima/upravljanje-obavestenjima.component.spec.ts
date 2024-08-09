import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpravljanjeObavestenjimaComponent } from './upravljanje-obavestenjima.component';

describe('UpravljanjeObavestenjimaComponent', () => {
  let component: UpravljanjeObavestenjimaComponent;
  let fixture: ComponentFixture<UpravljanjeObavestenjimaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UpravljanjeObavestenjimaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UpravljanjeObavestenjimaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
