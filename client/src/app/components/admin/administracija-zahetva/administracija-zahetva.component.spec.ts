import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AdministracijaZahetvaComponent } from './administracija-zahetva.component';

describe('AdministracijaZahetvaComponent', () => {
  let component: AdministracijaZahetvaComponent;
  let fixture: ComponentFixture<AdministracijaZahetvaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AdministracijaZahetvaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AdministracijaZahetvaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
