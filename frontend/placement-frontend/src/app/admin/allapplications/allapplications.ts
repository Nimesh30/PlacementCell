import { Component, signal } from '@angular/core';
// import { JobService } from 'app/Services/jobservice/jobservice';
import { ApplicationsService } from 'app/Services/applications-service';
import { DatePipe } from '@angular/common';

@Component({
  selector: 'app-allapplications',
  imports: [DatePipe],
  templateUrl: './allapplications.html',
  styleUrl: './allapplications.css',
})
export class Allapplications {

   totalApplication=localStorage.getItem('totalApplication');
  // currentPage=0;
  // totalPages=10;
  applications = signal<any[]>([]);
  companies = signal<any[]>([]);
  currentPage = signal(0);
  pageSize = 6;
  selectedCompany = signal('');
searchKeyword = signal('');
companyList: string[] = [];
selectedCompanies: string[] = [];

  totalPages = signal(0);

  constructor(private appService : ApplicationsService){ }

 onSearch(event: any) {

  const keyword = event.target.value;

  this.searchKeyword.set(keyword);
  this.currentPage.set(0);

  this.loadJobs();

}

filterByCompany(event: any) {

  const company = event.target.value;

  this.selectedCompany.set(company);
  this.currentPage.set(0);

  this.loadJobs();

}

  // companies=["Web","infosisy"];

ngOnInit(): void {

  this.loadJobs();

  this.appService.getAllCompanies()
  .subscribe((data: any) => {

    this.companies.set(["All", ...data]);

  });

}  
  
loadJobs() {

  this.appService.getStudentwithCompan(
    this.searchKeyword(),
    this.selectedCompany(),
    this.currentPage(),
    this.pageSize
  ).subscribe((data: any) => {

    this.applications.set(data.content);
    this.totalPages.set(data.totalPages);

  });

}

// loadCompanies(){
//   this.appService.getAllCompanies()
//   .subscribe((data:any)=>{
//     this.data
//   })
// }

onCompanyChange(event: any) {

  const company = event.target.value;

  if (company === 'All') {

    if (event.target.checked) {
      this.selectedCompanies = [...this.companyList.slice(1)];
    } else {
      this.selectedCompanies = [];
    }

  } else {

    if (event.target.checked) {
      this.selectedCompanies.push(company);
    } else {
      this.selectedCompanies = this.selectedCompanies.filter(c => c !== company);
    }

  }

  console.log(this.selectedCompanies);
}

nextPage() {
  if (this.currentPage() < this.totalPages() - 1) {
    this.currentPage.update(p => p + 1);
    this.loadJobs();
  }
}

prevPage() {
  if (this.currentPage() > 0) {
    this.currentPage.update(p => p - 1);
    this.loadJobs();
  }
}

}