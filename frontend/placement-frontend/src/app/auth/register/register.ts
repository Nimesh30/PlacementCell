import { Component } from '@angular/core';
import { Router,RouterLink } from '@angular/router';
import { HttpClient } from '@angular/common/http';
import { email } from '@angular/forms/signals';
import { FormsModule } from '@angular/forms';
@Component({
  selector: 'app-register',
  imports: [RouterLink,FormsModule],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {

    constructor(private router:Router ,private http:HttpClient){

    }

    email: string = '';
    username: string = '';
    studentId:string =''


    register() {
      console.log(this.studentId)
    const registerData = {
      username: this.username,
      studentId:this.studentId,
      email: this.email
      

    };

      console.log(registerData)

    this.http.post("http://localhost:8085/api/auth/register", registerData)
.subscribe({
  next: (response: any) => {
    alert(response.message); 
    this.router.navigate(['/login'])
  },
  error: (error) => {
    console.log("Full error:", error);

    //  Show backend message properly
    if (error.error && error.error.message) {
      alert(error.error.message);
    } else {
      alert("Registration failed");
    }
  }
});
}

  

    toLogin(){
      this.router.navigate(['/login'])
    }

    toHome(){
      this.router.navigate(['/'])
    }
}
