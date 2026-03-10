import { CommonModule, DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { JobService } from 'app/Services/jobservice/jobservice';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  selector: 'app-myapplications',
  imports: [CommonModule,DatePipe],
  templateUrl: './myapplications.html',
  styleUrl: './myapplications.css',
})
export class Myapplications {

  constructor(private jobservice:JobService,private cdr:ChangeDetectorRef){}

  applications: any[] = [];

// onstructor(private jobservice: JobService) {}

  ngOnInit(): void {
    const studentId = localStorage.getItem("studentId");

    if (studentId) {
      this.jobservice.getMyApplications(studentId).subscribe({
        next: (res) => {
          this.applications = res;
          this.cdr.detectChanges();
          console.log(this.applications);
        },
        error: (err) => {
          console.error("Error fetching applications:", err);
        }
      });
    }
  }
}
