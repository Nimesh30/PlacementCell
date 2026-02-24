import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Allaplications } from './allaplications';

describe('Allaplications', () => {
  let component: Allaplications;
  let fixture: ComponentFixture<Allaplications>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Allaplications]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Allaplications);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
