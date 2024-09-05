import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UnosOcenaComponent } from './unos-ocena.component';

describe('UnosOcenaComponent', () => {
  let component: UnosOcenaComponent;
  let fixture: ComponentFixture<UnosOcenaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [UnosOcenaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(UnosOcenaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
