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

  getAllJobs(keyword?: string, page: number = 0, size: number = 10) {
    return this.http.get<any>(
      `${this.baseUrl}/alljobs`,
      {
        params: {
          keyword: keyword || '',
          page: page,
          size: size
        }
      }
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

  getMyApplications(studentId: string, page: number, size: number) {

  return this.http.get<any>(
    `${this.apiUrl}/myApplications/${studentId}?page=${page}&size=${size}`
  );

}

applicationCount = signal(0);

setApplicationCount(count: number) {
  this.applicationCount.set(count);
}

loadApplicationCount(studentId: string) {

  const page = 0;
  const size = 5;

  this.getMyApplications(studentId, page, size).subscribe({
    next: (res) => {
      this.applicationCount.set(res.totalElements);
    }
  });

}


    private baseUrl1 = "http://localhost:8085/admin";

    // constructor(private http: HttpClient) {}

    exportStudents(jobId: number){
      return this.http.get(`${this.baseUrl1}/export/${jobId}`, {
        responseType: 'blob'
      });
    }


   deleteJob(jobId: number) {
   // console.log("calling service"+)
    return this.http.delete(
      `http://localhost:8085/api/jobs/deletejob/${jobId}`,
      { responseType: 'text' } // optional (if backend returns string)
    );

}

    updateJob(jobId: number, payload: any): Observable<any> {
    return this.http.put(`http://localhost:8085/api/jobs/updatejob/${jobId}`, payload);
  }

    jobsoffercount(studentid: string) {
      return this.http.get<number>(`http://localhost:8085/students/selected-count/${studentid}`);
  }

    
  }