import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
@Injectable({
  providedIn: 'root',
})
export class ApplicationsService {

  private baseUrl = 'http://localhost:8085/students';

  constructor(private http: HttpClient) {}

  getStudentwithCompan(
    keyword: string,
    company: string,
    status:string,
    page: number,
    size: number
  ) {
    return this.http.get(
      `${this.baseUrl}/studentwithCompanyStatus?keyword=${keyword}&company=${company}&status=${status}&page=${page}&size=${size}`
    );
  }

  getAllCompanies(){
    return this.http.get(
        `${this.baseUrl}/getAllcompanies`
    );
  }

 updateApplicationStatus(ids: number[], status: string) {
  return this.http.put(
    'http://localhost:8085/api/applications/updatestatus',
    {
      ids: ids,
      status: status
    },
    {
      responseType: 'text' // ✅ FIX
    }
  );
}



  //Notice Board services
  // getNotices() {
  //   return this.http.get('http://localhost:8080/api/students/notices');
  // }

  // createNotice(data: any) {
  //   return this.http.post('http://localhost:8080/api/admin/notices', data);
  // }

  getStatuses() {
  return this.http.get<string[]>('http://localhost:8085/api/applications/status');
  }

}
