// import { Component } from '@angular/core';

// @Component({
//   selector: 'app-myprofile',
//   imports: [],
//   templateUrl: './myprofile.html',
//   styleUrl: './myprofile.css',
// })
// export class Myprofile {

// }

import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators, ReactiveFormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'myprofile',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './myprofile.html',
  styleUrls: ['./myprofile.css']
})
export class Myprofile implements OnInit {

  academicForm!: FormGroup;
  selectedFile: File | null = null;

  constructor(private fb: FormBuilder) { }

  ngOnInit(): void {
    this.academicForm = this.fb.group({
      studentId: ['', Validators.required],
      fullName: ['', Validators.required],
      collegeEmail: ['', [Validators.required, Validators.email]],
      personalEmail: ['', [Validators.required, Validators.email]],
      mobileNumber: ['', [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      tenthMarks: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      twelfthMarks: ['', [Validators.required, Validators.min(0), Validators.max(100)]],
      stream: ['', Validators.required],
      bachelorsCgpa: ['', [Validators.required, Validators.min(0), Validators.max(10)]],
      mastersCgpa: [''],
      institute: ['', Validators.required],
      department: ['', Validators.required],
      passingYear: ['', Validators.required]
    });
  }

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  onSave(): void {
    if (this.academicForm.invalid) {
      this.academicForm.markAllAsTouched();
      return;
    }

    const formData = new FormData();
    formData.append('data', JSON.stringify(this.academicForm.value));

    if (this.selectedFile) {
      formData.append('resume', this.selectedFile);
    }

    console.log('Form Data:', this.academicForm.value);
    console.log('Resume File:', this.selectedFile);

    // Here you will call backend API
    // this.http.post("http://localhost:8085/students/add", formData).subscribe({
    
    // })
  }

  onCancel(): void {
    this.academicForm.reset();
    this.selectedFile = null;
  }
}