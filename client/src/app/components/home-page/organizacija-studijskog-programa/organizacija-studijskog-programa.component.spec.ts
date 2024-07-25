import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OrganizacijaStudijskogProgramaComponent } from './organizacija-studijskog-programa.component';

describe('OrganizacijaStudijskogProgramaComponent', () => {
  let component: OrganizacijaStudijskogProgramaComponent;
  let fixture: ComponentFixture<OrganizacijaStudijskogProgramaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OrganizacijaStudijskogProgramaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OrganizacijaStudijskogProgramaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
