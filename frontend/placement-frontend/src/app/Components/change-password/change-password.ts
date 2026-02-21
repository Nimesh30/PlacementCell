import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-change-password',
  standalone: true,
  imports: [FormsModule],
  templateUrl: './change-password.html',
  styleUrl: './change-password.css'
})
export class ChangePassword implements OnInit {

  email: string = '';
  newPassword: string = '';
  confirmPassword: string = '';

  constructor(private http: HttpClient, private router: Router) {}

  ngOnInit(): void {
    this.email = localStorage.getItem("userEmail") || '';
  }

  changePassword() {

    if (this.newPassword !== this.confirmPassword) {
      alert("Passwords do not match!");
      return;
    }

    const data = {
      email: this.email,
      newPassword: this.newPassword
    };

    this.http.post('http://localhost:8085/api/auth/change-password', data)
      .subscribe({
        next: () => {
          alert("Password changed successfully!");

          localStorage.removeItem("userEmail");

          this.router.navigate(['/dashboard']);
        },
        error: () => {
          alert("Failed to change password. Try again.");
        }
      });
  }
}