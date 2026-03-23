import { Component,EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Output } from '@angular/core';
@Component({
  selector: 'app-offer-received-model',
  standalone:true,
  imports: [CommonModule],
  templateUrl: './offer-received-model.html',
  styleUrl: './offer-received-model.css',
})
export class OfferReceivedModel {
   
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
