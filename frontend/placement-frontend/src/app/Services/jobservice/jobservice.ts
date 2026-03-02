import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Joblistings } from 'app/user/joblistings/joblistings';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private baseUrl = 'http://localhost:8085/api/jobs';

  constructor(private http: HttpClient) {}



  getAvailableJobs() {

  const keyword="das";

  return this.http.get<any[]>(`${this.baseUrl}/available?keyword=${keyword}`);
}

  addJob(job: any) {
    return this.http.post(`${this.baseUrl}/add`, job);
  }
}