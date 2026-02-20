import { Component } from '@angular/core';
import { Router,RouterLink } from '@angular/router';
@Component({
  selector: 'app-register',
  imports: [RouterLink],
  templateUrl: './register.html',
  styleUrl: './register.css',
})
export class Register {
    constructor(private router:Router ){

    }

    toLogin(){
      this.router.navigate(['/login'])
    }

    toHome(){
      this.router.navigate(['/'])
    }
}
