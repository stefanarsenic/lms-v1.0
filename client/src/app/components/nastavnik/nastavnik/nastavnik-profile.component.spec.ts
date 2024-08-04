import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NastavnikProfileComponent } from './nastavnik-profile.component';

describe('NastavnikComponent', () => {
  let component: NastavnikProfileComponent;
  let fixture: ComponentFixture<NastavnikProfileComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [NastavnikProfileComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(NastavnikProfileComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
