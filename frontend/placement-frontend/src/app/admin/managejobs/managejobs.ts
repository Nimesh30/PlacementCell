import { Component,signal,computed } from '@angular/core';
import { CommonModule } from '@angular/common';
// import { Addnewjobmodal } from 'app/addnewjobmodal/addnewjobmodal';
import { Addnewjobmodal } from '../../user/addnewjobmodal/addnewjobmodal';
import { JobService } from 'app/Services/jobservice/jobservice';
import { HttpClient } from '@angular/common/http';

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
  
  // constructor(private jobService:JobService){}
  constructor(private jobService: JobService,private http:HttpClient) {}

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

  downloadStudents(jobId: number, companyName: string){

  this.jobService.exportStudents(jobId).subscribe((data: Blob) => {

    const blob = new Blob([data], {
      type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet'
    });

    const url = window.URL.createObjectURL(blob);

    const a = document.createElement('a');
    a.href = url;
    a.download = companyName + '.xlsx';
    a.click();

    window.URL.revokeObjectURL(url);

  });

}

  toggleDescription(job: any) {
    job.showDescription = !job.showDescription;
  }


  openAddnewjobModal() {
    this.isOpen = true;
  }

  closeModal() {
    this.isOpen = false;
  }

}