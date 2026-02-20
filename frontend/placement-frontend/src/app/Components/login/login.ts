import { Component } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [RouterLink, FormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login {

  email: string = '';
  password: string = '';

  constructor(private router: Router, private http: HttpClient) {}

  login() {

    console.log(this.email)
    console.log(this.password)
  const loginData = {
    email: this.email,
    password: this.password
  };

  this.http.post<any>('http://localhost:8085/api/auth/loginUser', loginData)
    .subscribe({
      next: (response) => {

        if (response.firstLogin === true) {
          localStorage.setItem("userEmail", this.email);
          alert("Login Success.")
          this.router.navigate(['/change-password']);
        } else {
          this.router.navigate(['/dashboard']);
        }

      },
      error: (error) => {

        if (error.status === 401) {
          alert("Invalid Credentials");
        } else {
          alert("Something went wrong. Try again.");
        }

      }
    });
}
   
     

  goToRegister(){
    this.router.navigate(['/signup']);
  }

  toHome(){
    this.router.navigate(['/']);
  }
}