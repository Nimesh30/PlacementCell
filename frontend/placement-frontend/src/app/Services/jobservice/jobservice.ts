import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Joblistings } from 'app/user/joblistings/joblistings';
import { Observable } from 'rxjs';


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

  private apiUrl = "http://localhost:8085/api/applications";

  applyJob(studentId: String, jobId: number): Observable<any> {

    const body = {
      studentId: studentId,
      jobId: jobId
    };

    return this.http.post(`${this.apiUrl}/apply`, body);
  }

  getMyApplications(studentId: string): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/myApplications/${studentId}`);
  }
 
}