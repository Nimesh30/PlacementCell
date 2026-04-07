import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router'; // <-- Add ActivatedRoute
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

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
  token: string | null = null; // For holding the reset token
  isResetMode: boolean = false; // Flag to check which API to call

  // Inject ActivatedRoute to read the URL token
  constructor(private http: HttpClient, private router: Router, private route: ActivatedRoute,private toastr:ToastrService) { }

  ngOnInit(): void {
    // Check if we arrived here via a Reset Password email link
    this.token = this.route.snapshot.paramMap.get('token');

    if (this.token) {
      this.isResetMode = true;
    } else {
      // Normal First-Login Flow
      this.email = localStorage.getItem("userEmail") || '';
    }
  }

  submitPasswordChange() { // Renamed from changePassword() for clarity
    if (this.newPassword !== this.confirmPassword) {
      this.toastr.error("Passwords do not match!");
      return;
    }

    if (this.isResetMode) {
      // WORKFLOW: RESET PASSWORD VIA EMAIL LINK
      const resetData = {
        token: this.token,
        newPassword: this.newPassword
      };

      this.http.post('http://localhost:8085/api/auth/reset-password', resetData)
        .subscribe({
          next: () => {
            this.toastr.success("Password reset successfully!")
            this.router.navigate(['/login']); // <-- Auto redirect
          },
          error: (err) => {
            console.error(err);
            this.toastr.error("Failed to reset password. The link might be expired.")
          }
        });

    } else {
      // WORKFLOW: FIRST TIME LOGIN CHANGE PASSWORD
      const changeData = {
        email: this.email,
        newPassword: this.newPassword
      };

      this.http.post('http://localhost:8085/api/auth/change-password', changeData)
        .subscribe({
          next: () => {
            this.toastr.success("Password changed successfully!")
            localStorage.removeItem("userEmail");
            this.router.navigate(['/login']); // <-- Auto redirect
          },
          error: (err) => {
            console.error(err);
            this.toastr.error("Failed to change password. Try again.")
          }
        });
    }
  }
}


