import { Component, OnInit } from '@angular/core';
import { AdminService } from '../admin';
import { CommonModule } from '@angular/common';
import { ChangeDetectorRef } from '@angular/core';

@Component({
  selector: 'app-admindashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admindashboard.html',
  styleUrls: ['./admindashboard.css']
})
export class Admindashboard implements OnInit {

  dashboardData: any;

  constructor(private adminService: AdminService,
    private cdr:ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.adminService.getDashboard().subscribe({
      next: (data) => {
        console.log("Dashboard Data:", data);
        this.dashboardData = data;
        this.cdr.detectChanges()
      },
      error: (err) => {
        console.error("Error fetching dashboard:", err);
      }
    });
  }

}