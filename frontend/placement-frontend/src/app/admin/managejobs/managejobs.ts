import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Addnewjobmodal } from 'app/addnewjobmodal/addnewjobmodal';

@Component({
  selector: 'app-managejobs',
  standalone: true,
  imports: [CommonModule, Addnewjobmodal],  // ✅ Add CommonModule here
  templateUrl: './managejobs.html',
  styleUrl: './managejobs.css',
})
export class Managejobs {

  isOpen = false;

  openAddnewjobModal() {
    this.isOpen = true;
  }

  closeModal() {
    this.isOpen = false;
  }

}