import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-applymodal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './applymodal.html',
  styleUrls: ['./applymodal.css']
})
export class Applymodal {

  @Input() student: any;

  @Output() close = new EventEmitter<void>();
  @Output() submit = new EventEmitter<void>();

  submitApplication() {

    const confirmed = confirm('Are you sure you want to apply?');

    if (confirmed) {
      this.submit.emit();
      console.log("submitted your application");
    }
     
  }
}