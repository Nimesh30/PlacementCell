import { Component, OnInit, signal, computed } from '@angular/core';
import { JobService } from '../../Services/jobservice/jobservice';

@Component({
  selector: 'app-joblistings',
  imports: [],
  templateUrl: './joblistings.html',
  styleUrl: './joblistings.css',
})
export class Joblistings {
     // 🔹 Signals
  jobs = signal<any[]>([]);
  searchText = signal('');

  // 🔹 Computed Signal (Auto Filter)
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
}
