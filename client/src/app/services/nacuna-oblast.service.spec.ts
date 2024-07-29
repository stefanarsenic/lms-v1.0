import { TestBed } from '@angular/core/testing';

import { NacunaOblastService } from './nacuna-oblast.service';

describe('NacunaOblastService', () => {
  let service: NacunaOblastService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(NacunaOblastService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
