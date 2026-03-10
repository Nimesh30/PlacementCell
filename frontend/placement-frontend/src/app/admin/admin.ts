import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class AdminService {

  private baseUrl = "http://localhost:8085/admin";

  constructor(private http: HttpClient) { }

  getDashboard() {
    return this.http.get(`${this.baseUrl}/dashboard`);
  }


}