import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import {Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-adminlogin',
  imports: [RouterLink,FormsModule],
  templateUrl: './adminlogin.html',
  styleUrl: './adminlogin.css',
})
export class Adminlogin {

    email: string = '';
    password: string = '';

    constructor(private router: Router, private http: HttpClient) {}

    login() {

    const loginData = {
      email: this.email,
      password: this.password
    };

    this.http.post<any>('http://localhost:8085/admin/login', loginData)
      .subscribe({
       next: (response) => {

      localStorage.setItem("username", response.username);
      // localStorage.setItem("userEmail", response.email);

      if (response.firstLogin) {
      this.router.navigate(['/change-password']);
    } 
    else {
      this.router.navigate(['/adminlayout/admindashboard']);
    }
      } ,
        error: (error) => {

          if (error.status === 401) {
            alert("Invalid Credentials");
          } else {
            alert("Something went wrong. Try again.");
          }

        }
      });
  }
    
      
   
    toHome(){
      this.router.navigate(['/']);
    }

}
