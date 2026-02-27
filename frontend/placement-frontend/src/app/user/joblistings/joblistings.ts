import { Component, OnInit, signal, computed } from '@angular/core';
import { JobService } from 'app/Services/jobservice/jobservice';
import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { Applymodal } from 'app/applymodal/applymodal';

@Component({
  selector: 'app-joblistings',
  standalone: true,
  imports: [CommonModule, Applymodal],
  templateUrl: './joblistings.html',
  styleUrl: './joblistings.css',
})
export class Joblistings implements OnInit {

  jobs = signal<any[]>([]);
  searchText = signal('');
  selectedJob: any = null;
  studentData = signal<any | null>(null); // will store full profile

  filteredJobs = computed(() => {
    const search = this.searchText().toLowerCase();
    return this.jobs().filter(job =>
      job.companyName.toLowerCase().includes(search) ||
      job.jobTitle.toLowerCase().includes(search)
    );
  });

  constructor(private jobService: JobService, private http: HttpClient) {}

  ngOnInit(): void {
    // Load available jobs
    this.jobService.getAvailableJobs().subscribe(data => this.jobs.set(data));
  }


  updateSearch(event: Event) {
    const input = event.target as HTMLInputElement;
   // return input.value;
    this.searchText.set(input.value);
  }

  toggleDescription(job: any) {
    job.showDescription = !job.showDescription;
  }

  // ✅ Open modal and fetch full profile
  openApplyModal(job: any) {
    this.selectedJob = job;

    const studentId = localStorage.getItem('studentId');
    if (studentId) {
      this.http.get<any>(`http://localhost:8085/students/profile/${studentId}`)
        .subscribe(res => {
          this.studentData.set(res);
          console.log(this.studentData); // store full profile
        });
    } else {
      alert('Student not logged in');
    }
  }

  closeModal() {
    this.selectedJob = null;
  }
}