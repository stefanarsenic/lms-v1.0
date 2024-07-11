import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AppGenerickoComponent } from './app-genericko.component';

describe('AppGenerickoComponent', () => {
  let component: AppGenerickoComponent;
  let fixture: ComponentFixture<AppGenerickoComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AppGenerickoComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AppGenerickoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
