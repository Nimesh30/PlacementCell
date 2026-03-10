import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Joblistings } from 'app/user/joblistings/joblistings';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private baseUrl = 'http://localhost:8085/api/jobs';

  
  constructor(private http: HttpClient) {}



  getAvailableJobs(keyword?: string) {
     return this.http.get<any[]>(
        `${this.baseUrl}/available`,
      { params: { keyword: keyword || '' } }
    );
  }
  
  addJob(job: any) {
    console.log("In add job before return ...")
    return this.http.post(`${this.baseUrl}/add`, job);
  }
}