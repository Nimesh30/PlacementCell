import { Component } from '@angular/core';
import { RouterOutlet,RouterModule } from '@angular/router';

@Component({
  selector: 'app-adminlayout',
  imports: [RouterOutlet,RouterModule],
  templateUrl: './adminlayout.html',
  styleUrl: './adminlayout.css',
})
export class Adminlayout {
   username: string | null = '';

    ngOnInit() {
      this.username = localStorage.getItem("username");   
   }
}
