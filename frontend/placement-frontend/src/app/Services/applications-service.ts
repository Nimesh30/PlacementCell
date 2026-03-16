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
    page: number,
    size: number
  ) {
    return this.http.get(
      `${this.baseUrl}/studentwithCompanyStatus?keyword=${keyword}&company=${company}&page=${page}&size=${size}`
    );
  }

  getAllCompanies(){
    return this.http.get(
        `${this.baseUrl}/getAllcompanies`
    );
  }

}
