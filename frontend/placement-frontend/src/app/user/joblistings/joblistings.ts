import { Component, OnInit, signal, computed } from '@angular/core';
import { JobService } from '../../Services/jobservice/jobservice';
import { CommonModule } from '@angular/common';
import { Applymodal } from '../applymodal/applymodal';
import { HttpClient } from '@angular/common/http';
import { DomSanitizer } from '@angular/platform-browser';

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

  // ✅ FIXED (object, not array)

  studentData = signal<any | null>(null);
  selectedJob: any = null;

  constructor(private jobService: JobService, private http: HttpClient, public sanitizer: DomSanitizer) { }

  ngOnInit(): void {
    this.loadJobs();
    this.loadStudent();
  }

  // ✅ Load student once
  loadStudent() {
    const studentId = localStorage.getItem('studentId');
    if (studentId) {
      this.http
        .get<any>(`http://localhost:8085/students/profile/${studentId}`)
        .subscribe(res => {
          this.studentData.set(res);
        });
    }
  }

  // ✅ Load jobs (with backend search)
  loadJobs(keyword: string = '') {
    this.jobService.getAvailableJobs(keyword)
      .subscribe((data: any) => {
        this.jobs.set(data);
      });
  }

  // ✅ Search
  updateSearch(event: Event) {
    const value = (event.target as HTMLInputElement).value;
    this.searchText.set(value);
    this.loadJobs(value); // backend search
  }

  toggleDescription(job: any) {
    job.showDescription = !job.showDescription;
  }

  // ✅ Eligibility check
  isEligible(job: any): boolean {
    const student = this.studentData();
    if (!student) return false;

    return student.bachelorsCgpa >= job.minCgpa;
  }

  // ✅ Open modal ONLY if eligible
  openApplyModal(job: any) {
    const student = this.studentData();

    if (!student) {
      alert("Student not loaded");
      return;
    }

    if (this.isEligible(job)) {
      this.selectedJob = job; // ✅ opens modal
    } else {
      alert(`Not eligible! Min CGPA required: ${job.minCgpa}`);
    }

  }

  // ✅ Close modal
  closeModal() {
    this.selectedJob = null;
  }

  applyJob() {
    if (this.selectedJob) {
      this.selectedJob.applied = true;
    }
    this.closeModal();
  }

}