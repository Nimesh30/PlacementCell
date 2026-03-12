import { Component } from '@angular/core';

@Component({
  selector: 'app-allapplications',
  imports: [],
  templateUrl: './allapplications.html',
  styleUrl: './allapplications.css',
})
export class Allapplications {

  applications = [
    {
      student: 'Arjun Mehta',
      company: 'Infosys',
      applied: '2025-02-15',
      status: 'Selected'
    },
    {
      student: 'Rahul Patel',
      company: 'TCS',
      applied: '2025-02-14',
      status: 'Pending'
    },
    {
      student: 'Neha Shah',
      company: 'Google',
      applied: '2025-02-12',
      status: 'Rejected'
    }
  ];


}
