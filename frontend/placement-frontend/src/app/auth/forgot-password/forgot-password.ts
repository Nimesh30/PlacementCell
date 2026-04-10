import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-forgot-password',
  imports: [FormsModule],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css',
})
export class ForgotPassword {

  email: string = "";

  constructor(private http: HttpClient,private toastr:ToastrService) { }

  sendResetLink() {

    this.http.post(
      `http://localhost:8085/api/auth/forgotPassword?email=${this.email}`,
      {}
    )
      .subscribe({
        next: (res: any) => {
          this.toastr.show("OTP sent to email")
        },
        error: (err) => {
          console.log(err)
          this.toastr.show("Error in send reset OTP")
        }
      });

  }

}