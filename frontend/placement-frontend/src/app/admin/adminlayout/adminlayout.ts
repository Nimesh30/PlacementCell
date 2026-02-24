import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-adminlayout',
  imports: [RouterOutlet],
  templateUrl: './adminlayout.html',
  styleUrl: './adminlayout.css',
})
export class Adminlayout {
   username: string | null = '';

    ngOnInit() {
      this.username = localStorage.getItem("username");   
   }
}
