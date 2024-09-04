import { TestBed } from '@angular/core/testing';

import { ZahtevMaterijalaService } from './zahtev-materijala.service';

describe('ZahtevMaterijalaService', () => {
  let service: ZahtevMaterijalaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ZahtevMaterijalaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
