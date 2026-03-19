// import { Component } from '@angular/core';
// import { FormsModule } from '@angular/forms';
// import { NoticeService } from 'app/Services/noticeBoard/notice';
// import { HttpClient } from '@angular/common/http';

// @Component({
//   selector: 'app-adminnoticeboard',
//   standalone: true,
//   imports: [FormsModule],
//   templateUrl: './adminnoticeboard.html',
//   styleUrls: ['./adminnoticeboard.css'],
// })
// export class Adminnoticeboard {

//   notice = {
//     title: '',
//     message: '',
//     fileUrl: '',
//     pinned: false
//   };
  

//   constructor(private noticeService: NoticeService) { }


//   // createNotice(data: any) {
//   //   console.log("Create notice data ", this.http.post('http://localhost:8085/api/admin/notices', data));
//   //   return this.http.post('http://localhost:8085/api/admin/notices', data);
//   // }

//   submitNotice() {
//     this.noticeService.createNotice(this.notice).subscribe({
//       next: (res) => {
//         alert('Notice Created Successfully ');
//         this.resetForm();
//       },
//       error: (err) => {
//         console.error(err);
//         alert('Error creating notice ');
//       }
//     });
//   }

//   resetForm() {
//     this.notice = {
//       title: '',
//       message: '',
//       fileUrl: '',
//       pinned: false
//     };
//   }
// }






import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NoticeService } from 'app/Services/noticeBoard/notice';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-adminnoticeboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './adminnoticeboard.html',
  styleUrls: ['./adminnoticeboard.css']
})
export class Adminnoticeboard implements OnInit {

  notices: any[] = [];

  notice = {
    id: null,
    title: '',
    message: '',
    fileUrl: '',
    pinned: false
  };

  isEditMode = false;

  constructor(private noticeService: NoticeService,private cdr:ChangeDetectorRef) { }

  ngOnInit(): void {
    this.loadNotices();
    this.cdr.detectChanges()
  }

  loadNotices() {
    this.noticeService.getNotices().subscribe((data: any) => {
      this.notices = data;
      this.cdr.detectChanges()
    });
  }

  submitNotice() {

    if (this.isEditMode) {
      this.noticeService.updateNotice(this.notice.id!, this.notice)
        .subscribe(() => {
          alert("Notice Updated ✅");
          this.resetForm();
          this.loadNotices();
          this.cdr.detectChanges()
        });
    } else {
      this.noticeService.createNotice(this.notice)
        .subscribe(() => {
          alert("Notice Created ✅");
          this.resetForm();
          this.loadNotices();
          this.cdr.detectChanges()
        });
    }
  }

  editNotice(n: any) {
    this.notice = { ...n };
    this.isEditMode = true;
  }

  deleteNotice(id: number) {
    if (confirm("Delete this notice?")) {
      this.noticeService.deleteNotice(id).subscribe(() => {
        alert("Notice Deleted ❌");
        this.loadNotices();
        this.cdr.detectChanges()
      });
    }
  }

  resetForm() {
    this.notice = {
      id: null,
      title: '',
      message: '',
      fileUrl: '',
      pinned: false
    };
    this.isEditMode = false;
  }
}