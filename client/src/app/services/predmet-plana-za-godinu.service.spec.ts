import { TestBed } from '@angular/core/testing';

import { PredmetPlanaZaGodinuService } from './predmet-plana-za-godinu.service';

describe('PredmetPlanaZaGodinuService', () => {
  let service: PredmetPlanaZaGodinuService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PredmetPlanaZaGodinuService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
