import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UredjivanjeSilabusaComponent } from './uredjivanje-silabusa.component';

describe('UredjivanjeSilabusaComponent', () => {
  let component: UredjivanjeSilabusaComponent;
  let fixture: ComponentFixture<UredjivanjeSilabusaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UredjivanjeSilabusaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UredjivanjeSilabusaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
