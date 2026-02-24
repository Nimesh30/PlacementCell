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
    console.log('Application Submitted');
    this.submit.emit();
  }
}