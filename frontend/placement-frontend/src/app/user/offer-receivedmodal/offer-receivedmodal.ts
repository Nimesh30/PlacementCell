import { Component,EventEmitter } from '@angular/core';
import { Output } from '@angular/core';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-offer-receivedmodal',
  imports: [RouterOutlet],
  templateUrl: './offer-receivedmodal.html',
  styleUrl: './offer-receivedmodal.css',
})
export class OfferReceivedmodal {
  
 @Output() closeModal = new EventEmitter<void>();

  close() {
    this.closeModal.emit();   // 🔥 send event to parent
  }

  acceptOffer() {
    console.log("Accepted");
    this.close();
  }

  declineOffer() {
    console.log("Declined");
    this.close();
  }
}
