import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'   // ✅ MUST BE PRESENT
})
export class NoticeService {

  constructor(private http: HttpClient) { }

  createNotice(data: any) {
    return this.http.post('http://localhost:8085/api/admin/notices', data);
  }

  getNotices() {
    return this.http.get('http://localhost:8085/api/students/notices');
  }
  updateNotice(id: number, data: any) {
    return this.http.put(`http://localhost:8085/api/admin/notices/${id}`, data);
  }

  deleteNotice(id: number) {
    return this.http.delete(`http://localhost:8085/api/admin/notices/${id}`);
  }

}