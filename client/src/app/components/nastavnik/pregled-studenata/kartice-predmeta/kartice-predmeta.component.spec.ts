import { ComponentFixture, TestBed } from '@angular/core/testing';

import { KarticePredmetaComponent } from './kartice-predmeta.component';

describe('KarticePredmetaComponent', () => {
  let component: KarticePredmetaComponent;
  let fixture: ComponentFixture<KarticePredmetaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [KarticePredmetaComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(KarticePredmetaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
