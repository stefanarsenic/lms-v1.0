import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenerickaTabelaComponent } from './genericka-tabela.component';

describe('GenerickaTabelaComponent', () => {
  let component: GenerickaTabelaComponent;
  let fixture: ComponentFixture<GenerickaTabelaComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenerickaTabelaComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(GenerickaTabelaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
