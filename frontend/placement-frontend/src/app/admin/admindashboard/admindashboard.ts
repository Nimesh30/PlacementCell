import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { AdminService } from '../admin';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-admindashboard',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './admindashboard.html',
  styleUrl: './admindashboard.css',
})
export class Admindashboard implements OnInit {

  dashboardData: any = {};
  constructor(private adminService: AdminService,
    private cdr: ChangeDetectorRef,
  ) { }

  ngOnInit() {
    this.adminService.getDashboard().subscribe((data) => {
      this.dashboardData = data;
      this.cdr.detectChanges();
      
    });
  }


  }

