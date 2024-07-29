import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UverenjeOProsecnojOceniComponent } from './uverenje-o-prosecnoj-oceni.component';

describe('UverenjeOProsecnojOceniComponent', () => {
  let component: UverenjeOProsecnojOceniComponent;
  let fixture: ComponentFixture<UverenjeOProsecnojOceniComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UverenjeOProsecnojOceniComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UverenjeOProsecnojOceniComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
