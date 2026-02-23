import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class JobService {

  private baseUrl = 'http://localhost:8085/api/jobs';

  constructor(private http: HttpClient) {}

  getAvailableJobs() {
    return this.http.get(`${this.baseUrl}/available`);
  }

  addJob(job: any) {
    return this.http.post(`${this.baseUrl}/add`, job);
  }
}