import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SpisakStudenataNastavnikComponent } from './spisak-studenata-nastavnik.component';

describe('SpisakStudenataNastavnikComponent', () => {
  let component: SpisakStudenataNastavnikComponent;
  let fixture: ComponentFixture<SpisakStudenataNastavnikComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SpisakStudenataNastavnikComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SpisakStudenataNastavnikComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
