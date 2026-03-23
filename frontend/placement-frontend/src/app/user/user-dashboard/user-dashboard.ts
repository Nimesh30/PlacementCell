import { Component, OnInit, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { JobService } from '../../Services/jobservice/jobservice';
import { OfferReceivedModel } from '../offer-received-model/offer-received-model';
// import { OfferReceivedmodal } from '../offer-receivedmodal/offer-receivedmodal';
import { ChangeDetectorRef } from '@angular/core';
@Component({
  selector: 'app-user-dashboard',
  standalone: true,
  imports: [CommonModule,OfferReceivedModel],
  templateUrl: './user-dashboard.html',
  styleUrl: './user-dashboard.css',
})
export class UserDashboard implements OnInit {

  // Signals
  username = signal<string | null>('');
  jobs = signal<any[]>([]);
  totaljobs=signal(0);
  totaloffers=signal(0);
  isOfferModalOpen = signal(false);
  constructor(public jobService:JobService,private cdr:ChangeDetectorRef) {}

  ngOnInit(): void {

    // Set username from localStorage
    this.username.set(localStorage.getItem("username"));
    const studentId = localStorage.getItem("studentId");
    // Fetch jobs
    this.jobService.getAvailableJobs().subscribe((data: any[]) => {
      this.jobs.set(data);
      this.totaljobs.set(data.length)
    });

    if(studentId){
    this.jobService.loadApplicationCount(studentId);
    }

    this.getstudenttotaloffers();

  }

  getInitial(companyName: string): string {
    return companyName?.charAt(0).toUpperCase();
  }

getstudenttotaloffers() {
  const studentId = localStorage.getItem("studentId");

  if (studentId) {
    this.jobService.jobsoffercount(studentId).subscribe({
      next: (res: number) => {
        this.totaloffers.set(res);
      },
      error: (err) => {
        console.error("Failed to fetch offers count:", err);
      }
    });
  }
}

// openOfferModal() {
//   console.log("clicked ...1");
//   this.isOfferModalOpen.set(true);
//   console.log("Model open in first condition...")
// }


  openOfferModal() {
    console.log("clicked");
    if (!this.isOfferModalOpen()) {
      console.log("Model open in second condition...")
      this.cdr.detectChanges();
      this.isOfferModalOpen.set(true);
    }
  }

closeOfferModal() {
  this.isOfferModalOpen.set(false);
}


}