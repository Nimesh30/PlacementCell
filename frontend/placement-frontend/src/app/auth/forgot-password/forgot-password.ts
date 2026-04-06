import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-forgot-password',
  imports: [FormsModule],
  templateUrl: './forgot-password.html',
  styleUrl: './forgot-password.css',
})
export class ForgotPassword {

  email: string = "";

  constructor(private http: HttpClient) { }

  sendResetLink() {

    this.http.post(
      `http://localhost:8085/api/auth/forgotPassword?email=${this.email}`,
      {}
    )
      .subscribe({
        next: (res: any) => {
          alert("Reset link sent to email");
        },
        error: (err) => {
          console.log(err)
          alert("Error sending reset link");
        }
      });

  }

}