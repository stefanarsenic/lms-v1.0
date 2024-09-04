import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ZahtevMaterijalaComponent } from './zahtev-materijala.component';

describe('ZahtevMaterijalaComponent', () => {
  let component: ZahtevMaterijalaComponent;
  let fixture: ComponentFixture<ZahtevMaterijalaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ZahtevMaterijalaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ZahtevMaterijalaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
