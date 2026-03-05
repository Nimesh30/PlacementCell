import { Component,signal,computed } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { Addnewjobmodal } from 'app/addnewjobmodal/addnewjobmodal';
import { Addnewjobmodal } from '../../addnewjobmodal/addnewjobmodal';
import { JobService } from 'app/Services/jobservice/jobservice';

@Component({
  selector: 'app-managejobs',
  standalone: true,
  imports: [CommonModule,Addnewjobmodal],  //  Add CommonModule here
  templateUrl: './managejobs.html',
  styleUrl: './managejobs.css',
})
export class Managejobs {

  isOpen = false;
  jobs = signal<any[]>([]);
  totaljobs=signal(0);
  searchText = signal('');

  
  filteredJobs = computed(() => {
    const search = this.searchText().toLowerCase();

    return this.jobs().filter(job => 
    job.companyName.toLowerCase().includes(search) ||
    job.jobTitle.toLowerCase().includes(search));
  });
  
  constructor(private jobService:JobService){}
  // constructor(private jobService: JobService,private http:HttpClient) {}

  ngOnInit(): void {
    this.loadJobs();

  }

  loadJobs(keyword: string = '') {

  this.jobService.getAvailableJobs(keyword)
    .subscribe((data: any) => {
      this.jobs.set(data);
      this.totaljobs.set(data.length)
    //  console.log(this.totaljobs)
    });

  }

   updateSearch(event: Event) {

    const input = event.target as HTMLInputElement;
    const value = input.value;

    this.searchText.set(value);

    // Call backend
    this.loadJobs(value);
  }


  
  


  openAddnewjobModal() {
    this.isOpen = true;
  }

  closeModal() {
    this.isOpen = false;
  }

}