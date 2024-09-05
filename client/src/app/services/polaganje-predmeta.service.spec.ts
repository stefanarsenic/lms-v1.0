import { TestBed } from '@angular/core/testing';

import { PolaganjePredmetaService } from './polaganje-predmeta.service';

describe('PolaganjePredmetaService', () => {
  let service: PolaganjePredmetaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PolaganjePredmetaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
