import { Component, OnInit, signal, computed } from '@angular/core';
import { JobService } from '../../Services/jobservice/jobservice';
import { CommonModule } from '@angular/common';
import { Applymodal } from '../applymodal/applymodal';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-joblistings',
  standalone: true,
  imports: [CommonModule, Applymodal],
  templateUrl: './joblistings.html',
  styleUrl: './joblistings.css',
})
export class Joblistings {

  jobs = signal<any[]>([]);
  searchText = signal('');
  studentData=signal<any[]>([]);

  //  Selected job for modal


  selectedJob: any = null;

  filteredJobs = computed(() => {
    const search = this.searchText().toLowerCase();

    return this.jobs().filter(job => 
    job.companyName.toLowerCase().includes(search) ||
    job.jobTitle.toLowerCase().includes(search));
  });


  constructor(private jobService: JobService,private http:HttpClient) {}

  ngOnInit(): void {
    this.loadJobs();
  }

  loadJobs(keyword: string = '') {

  this.jobService.getAvailableJobs(keyword)
    .subscribe((data: any) => {
      this.jobs.set(data);
    });

  }

  // updateSearch(event: Event) {
  //   const input = event.target as HTMLInputElement;
  //   this.searchText.set(input.value);
  // }

  updateSearch(event: Event) {

    const input = event.target as HTMLInputElement;
    const value = input.value;

    this.searchText.set(value);

    // Call backend
    this.loadJobs(value);
  }

  toggleDescription(job: any) {
    job.showDescription = !job.showDescription;
  }

  applyJob() {

    if (this.selectedJob) {
      this.selectedJob.applied = true;
    }

    this.closeModal();
  }


  //  OPEN MODAL
  openApplyModal(job: any) {
  this.selectedJob = job;

  const studentId = localStorage.getItem('studentId');
  if (studentId) {
    this.http.get<any>(`http://localhost:8085/students/profile/${studentId}`)
      .subscribe(res => {

        console.log(res); // store full profile
        this.studentData.set(res);
        console.log(this.studentData); // store full profile
      });
  } else {
    alert('Student not logged in');
  }
}

  //  CLOSE MODAL
  closeModal() {
    this.selectedJob = null;
  }
}