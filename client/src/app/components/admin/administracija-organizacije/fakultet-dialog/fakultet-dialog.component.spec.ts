import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FakultetDialogComponent } from './fakultet-dialog.component';

describe('FakultetDialogComponent', () => {
  let component: FakultetDialogComponent;
  let fixture: ComponentFixture<FakultetDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FakultetDialogComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FakultetDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
