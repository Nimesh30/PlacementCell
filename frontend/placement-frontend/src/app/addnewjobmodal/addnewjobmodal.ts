import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ReactiveFormsModule, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { JobService } from 'app/Services/jobservice/jobservice';

@Component({
  selector: 'app-addnewjobmodal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './addnewjobmodal.html',
  styleUrl: './addnewjobmodal.css',
})
export class Addnewjobmodal {

  @Output() close = new EventEmitter<void>();

  jobForm: FormGroup;
  isSubmitting = false;   // ✅ ADD THIS LINE

  constructor(
    private fb: FormBuilder,
    private jobService: JobService
  ) {
    this.jobForm = this.fb.group({
      companyName: ['', Validators.required],
      jobTitle: ['', Validators.required],
      packageLpa: [null],
      location: [''],
      minCgpa: [null],
      deadline: ['', Validators.required],
      eligibleDegrees: [''],
      description: ['', Validators.required],
    });
  }

  closeModal() {
    this.close.emit();
  }

  submitJob() {

    if (this.jobForm.invalid) {
      this.jobForm.markAllAsTouched();
      return;
    }

    this.isSubmitting = true;

    this.jobService.addJob(this.jobForm.value).subscribe({
      next: () => {
        alert('Job added successfully!');
        this.isSubmitting = false;
        this.jobForm.reset();
        this.closeModal();
      },
      error: (err) => {
        console.error(err);
        alert('Failed to add job');
        this.isSubmitting = false;
      }
    });
  }

}