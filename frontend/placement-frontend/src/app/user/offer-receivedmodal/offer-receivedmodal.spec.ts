import { ComponentFixture, TestBed } from '@angular/core/testing';

import { OfferReceivedmodal } from './offer-receivedmodal';

describe('OfferReceivedmodal', () => {
  let component: OfferReceivedmodal;
  let fixture: ComponentFixture<OfferReceivedmodal>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [OfferReceivedmodal]
    })
    .compileComponents();

    fixture = TestBed.createComponent(OfferReceivedmodal);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
