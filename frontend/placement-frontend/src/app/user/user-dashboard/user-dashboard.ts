import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobService } from '../../Services/jobservice/jobservice';

@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './user-dashboard.html',
  styleUrl: './user-dashboard.css',
})
export class UserDashboard implements OnInit {

  // ✅ Signals
  username = signal<string | null>('');
  jobs = signal<any[]>([]);
  totaljobs=signal(0);
  constructor(private jobService:JobService) {}

  ngOnInit(): void {

    // Set username from localStorage
    this.username.set(localStorage.getItem("username"));

    // Fetch jobs
    this.jobService.getAvailableJobs().subscribe((data: any[]) => {
      this.jobs.set(data);
      this.totaljobs.set(data.length)
    });
  }

  getInitial(companyName: string): string {
    return companyName?.charAt(0).toUpperCase();
  }
}