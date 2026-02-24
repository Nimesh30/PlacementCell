import { Component, OnInit, signal, computed } from '@angular/core';
import { JobService } from 'app/Services/jobservice/jobservice';
import { CommonModule } from '@angular/common';
import { Applymodal } from 'app/applymodal/applymodal';

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

  // ✅ Selected job for modal
  selectedJob: any = null;

  filteredJobs = computed(() => {
    const search = this.searchText().toLowerCase();
    return this.jobs().filter(job =>
      job.companyName.toLowerCase().includes(search) ||
      job.jobTitle.toLowerCase().includes(search)
    );
  });

  constructor(private jobService: JobService) {}

  ngOnInit(): void {
    this.jobService.getAvailableJobs().subscribe((data: any) => {
      this.jobs.set(data);
    });
  }

  updateSearch(event: Event) {
    const input = event.target as HTMLInputElement;
    this.searchText.set(input.value);
  }

  toggleDescription(job: any) {
    job.showDescription = !job.showDescription;
  }

  // ✅ OPEN MODAL
  openApplyModal(job: any) {
    this.selectedJob = job;
  }

  // ✅ CLOSE MODAL
  closeModal() {
    this.selectedJob = null;
  }
}