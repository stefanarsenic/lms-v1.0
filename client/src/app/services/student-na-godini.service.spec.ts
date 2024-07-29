import { TestBed } from '@angular/core/testing';

import { StudentNaGodiniService } from './student-na-godini.service';

describe('StudentService', () => {
  let service: StudentNaGodiniService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StudentNaGodiniService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
