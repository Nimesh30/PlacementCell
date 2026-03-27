import { Component, Input, Output, EventEmitter, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormGroup, FormControl, ReactiveFormsModule } from '@angular/forms';
import { JobService } from 'app/Services/jobservice/jobservice';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-addnewjobmodal',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './addnewjobmodal.html',
  styleUrls: ['./addnewjobmodal.css']
})
export class AddNewJobModal implements OnInit {
  @Input() jobData: any = null;  // <-- receive job for editing
  @Output() close = new EventEmitter<void>();

  jobForm: FormGroup = new FormGroup({
    companyName: new FormControl(''),
    jobTitle: new FormControl(''),
    packageLpa: new FormControl(''),
    location: new FormControl(''),
    minCgpa: new FormControl(''),
    deadline: new FormControl(''),
    eligibleDegrees: new FormControl(''),
    description: new FormControl(''),
  });

  constructor(private jobService: JobService , private cdr:ChangeDetectorRef) {}

  ngOnInit(): void {
    if (this.jobData) {
      // Pre-fill form when editing
      this.jobForm.patchValue({
        companyName: this.jobData.companyName,
        jobTitle: this.jobData.jobTitle,
        packageLpa: this.jobData.packageLpa,
        location: this.jobData.location,
        minCgpa: this.jobData.minCgpa,
        deadline: this.jobData.deadline,
        eligibleDegrees: this.jobData.eligibleDegrees,
        description: this.jobData.description
      });
    }
  }

  submitJob() {
    const payload = this.jobForm.value;

    if (this.jobData?.id) {
      // Update job
      this.jobService.updateJob(this.jobData.id, payload).subscribe({
        next: () => {
          alert('Job updated successfully ');
          this.close.emit();
        },
        error: (err) => {
          console.error(err);
          alert('Update failed ');
        }
      });
    } else {
      // Create job
      this.jobService.addJob(payload).subscribe({
        next: () => {
          alert('Job posted successfully ');
          this.close.emit();
        },
        error: (err) => {
          console.error(err);
          alert('Post failed ');
        }
      });
    }
  }


  closeModal(){
    this.close.emit();
  }
}