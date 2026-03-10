import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobService } from 'app/Services/jobservice/jobservice';
@Component({
  selector: 'app-applymodal',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './applymodal.html',
  styleUrls: ['./applymodal.css']
})
export class Applymodal {

  @Input() student: any;
  @Input() job:any;
  @Output() close = new EventEmitter<void>();
  @Output() submit = new EventEmitter<void>();

  constructor(private jobservice:JobService){}
  submitApplication() {

    console.log(this.student.student); 
    console.log(this.job);

    if(confirm('Are you sure you want to apply')){

    console.log(this.student); 
    console.log(this.job);

    const studentId = this.student.studentId;
    const jobId = this.job.id;

    console.log(studentId, jobId);

    this.jobservice.applyJob(studentId, jobId)
      .subscribe({
        next: (res) => {
          alert("Application submitted");
        },
        error: (err) => {
          console.error(err);
          alert("you already applied")
        }
      });

  }
     this.submit.emit();
   //  console.log("submit your application");

     
  }
}