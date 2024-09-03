import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministracijaOrganizacijeComponent } from './administracija-organizacije.component';

describe('AdministracijaOrganizacijeComponent', () => {
  let component: AdministracijaOrganizacijeComponent;
  let fixture: ComponentFixture<AdministracijaOrganizacijeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministracijaOrganizacijeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministracijaOrganizacijeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
