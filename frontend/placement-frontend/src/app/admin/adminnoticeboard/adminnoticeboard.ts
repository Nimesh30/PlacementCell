import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { NoticeService } from 'app/Services/noticeBoard/notice';

@Component({
  selector: 'app-adminnoticeboard',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './adminnoticeboard.html',
  styleUrls: ['./adminnoticeboard.css']
})
export class Adminnoticeboard implements OnInit {

  notices: any[] = [];

  showForm = false;
  isEditMode = false;

  notice = {
    id: null as number | null,
    title: '',
    message: '',
    fileUrl: '',
    pinned: false
  };

  constructor(
    private noticeService: NoticeService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.loadNotices();
  }

  /* ---------------- LOAD NOTICES ---------------- */

  loadNotices() {
    this.noticeService.getNotices().subscribe({
      next: (data: any) => {

        console.log("API Response:", data);

        this.notices = data;
        
        // Force UI refresh
        this.cdr.detectChanges();

      },
      error: (err) => {
        console.error("Error loading notices", err);
      }
    });
  }

  /* ---------------- OPEN FORM ---------------- */

  openForm() {
    this.showForm = true;
  }

  closeForm() {
    this.showForm = false;
    this.resetForm();
  }

  /* ---------------- CREATE / UPDATE ---------------- */

  submitNotice() {

    if (this.isEditMode) {

      this.noticeService.updateNotice(this.notice.id!, this.notice)
        .subscribe({
          next: () => {
            alert("Notice Updated");
            this.afterSubmit();
          },
          error: () => alert("Error updating notice")
        });

    } else {

      this.noticeService.createNotice(this.notice)
        .subscribe({
          next: () => {
            alert("Notice Created");
            this.afterSubmit();
          },
          error: () => alert("Error creating notice")
        });

    }
  }

  /* ---------------- AFTER SUBMIT ---------------- */

  afterSubmit() {
    this.closeForm();
    this.resetForm();
    this.loadNotices();
  }


  toggleNotice(selected: any) {
    this.notices.forEach(n => {
      if (n !== selected) n.expanded = false;
    });
    selected.expanded = !selected.expanded;
  }

  /* ---------------- EDIT ---------------- */

  editNotice(n: any) {
    this.notice = { ...n };
    this.isEditMode = true;
    this.showForm = true;
  }

  /* ---------------- DELETE ---------------- */

  deleteNotice(id: number) {

    if (confirm("Delete this notice?")) {

      this.noticeService.deleteNotice(id).subscribe({
        next: () => {
          alert("Notice Deleted");
          console.log("after delete before loadNotice")
          this.loadNotices();
          console.log("after delete after loadNotice")
        },
        error: () =>{ alert("Error deleting notice")
          console.log("In error ");
        }
      });
    }

  }

  /* ---------------- RESET ---------------- */

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