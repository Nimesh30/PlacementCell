import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClient } from '@angular/common/http';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-allstudents',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './allstudents.html',
  styleUrls: ['./allstudents.css']
})
export class Allstudents implements OnInit {

  students: any[] = [];
  filteredStudents: any[] = [];

  searchText: string = "";
  filterType: string = "all";

  placedCount = 0;
  notPlacedCount = 0;

  constructor(private http: HttpClient,private cdr:ChangeDetectorRef) { }

  ngOnInit() {
    this.loadStudents();
  }

  loadStudents() {

    this.http.get<any[]>("http://localhost:8085/admin/students")
      .subscribe(data => {
          console.log("Admin Students....",data);
        this.students = data;

        this.placedCount = this.students.filter(s => s.status === "SELECTED").length;
        this.notPlacedCount = this.students.filter(s => s.status !== "SELECTED").length;
        this.applyFilters();
        this.cdr.detectChanges();

      });

  }

  applyFilters() {

    this.filteredStudents = this.students.filter(student => {

      const searchMatch =
        student.name.toLowerCase().includes(this.searchText.toLowerCase()) ||
        student.studentId.toLowerCase().includes(this.searchText.toLowerCase());

      let filterMatch = true;
      if (this.filterType === "selected") {
        filterMatch = student.status === "SELECTED";
        console.log("Filter type in first if ",filterMatch)
      }
      
      if (this.filterType === "notPlaced") {
        filterMatch = student.status !== "SELECTED";
        console.log("Filter type in second if ",filterMatch)
      }

      // if (this.filterType === "notPlaced") {
      //   filterMatch = student.status === "REJECTED";
      //   console.log("Filter type in THIRD if ",filterMatch)
      // }

      return searchMatch && filterMatch;

    });

  }

  setFilter(type: string) {
    this.filterType = type;
    this.applyFilters();
  }

}