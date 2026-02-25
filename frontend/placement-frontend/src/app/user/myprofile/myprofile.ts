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
  isEditMode = false;

  baseUrl = 'http://localhost:8085/students';

  constructor(private fb: FormBuilder, private http: HttpClient) { }

  ngOnInit(): void {
      
    this.academicForm = this.fb.group({
      studentId: [{ value: '', disabled: true }],
      collegeEmail: [{ value: '', disabled: true }],
      

      fullName: ['', { disabled: true } ,Validators.required],
      personalEmail: ['', { disabled: true }, [Validators.required, Validators.email]],
      mobileNumber: ['', { disabled: true }, [Validators.required, Validators.pattern(/^[0-9]{10}$/)]],
      tenthMarks: ['', { disabled: true }, [Validators.required, Validators.min(0), Validators.max(100)]],
      twelfthMarks: ['', { disabled: true }, [Validators.required, Validators.min(0), Validators.max(100)]],
      stream: ['', { disabled: true }, Validators.required],
      bachelorsCgpa: ['', { disabled: true }, [Validators.required, Validators.min(0), Validators.max(10)]],
      mastersCgpa: ['', { disabled: true }],
      institute: ['', { disabled: true }, Validators.required],
      department: ['', { disabled: true }, Validators.required],
      passingYear: ['', { disabled: true }, Validators.required]
    });
   
    const studentId = localStorage.getItem('studentId');
    const collegeEmail = localStorage.getItem('collegeEmail');
    
    console.log("In profile before if ",studentId)
    if (studentId) {
      console.log(" 1. myprofile IF condition id " +studentId)
      this.fetchProfile(studentId);
      console.log("After fetch profile")
    } else {
      alert("Student not logged in for fetch");
    }
  }

  // ---------------- FETCH PROFILE ----------------

  fetchProfile(studentId: string) {
      console.log("1.1 In fetch...")
    this.http.get<any>(`${this.baseUrl}/profile/${studentId}`)
      .subscribe({
        // Not going in this part 12 : 05
        next: (response) => {
          console.log("saved profile ",response)
          localStorage.setItem("studentId", response.studentId);
          localStorage.setItem("collegeEmail", response.collegeEmail)
          setTimeout(() => {
            this.isEditMode = true;
          });
          this.academicForm.patchValue(response);

          console.log("Profile exists → Edit mode");

        },
        error: () => {

          // First time user
          setTimeout(() => {
            this.isEditMode = false;
          });

          this.academicForm.patchValue({
            studentId: studentId,
            collegeEmail: localStorage.getItem('collegeEmail')
          });

          console.log("First time user → Create mode");

        }
      });
  }

  // ---------------- FILE CHANGE ----------------

  onFileChange(event: Event): void {
    const input = event.target as HTMLInputElement;

    if (input.files && input.files.length > 0) {
      this.selectedFile = input.files[0];
    }
  }

  // ---------------- SAVE PROFILE ----------------

  onSave(): void {

    if (this.academicForm.invalid) {
      this.academicForm.markAllAsTouched();
      return;
    }

    const studentId = localStorage.getItem('studentId');
    if (!studentId) {
      alert("Student not logged for save");
      return;
    }

    const rawData = this.academicForm.getRawValue();
    const formData = new FormData();

    // Append all form fields
    Object.keys(rawData).forEach(key => {
      formData.append(key, rawData[key]);
    });

    // Append file if selected
    if (this.selectedFile) {
      formData.append('file', this.selectedFile);
    }

    if (this.isEditMode) {

      // UPDATE PROFILE
      this.http.put(`${this.baseUrl}/update/${studentId}`, formData)
        .subscribe({
          next: () => alert("Profile updated successfully!")
          // error: () => alert("Error updating profile")
        });

    } else {

      // CREATE PROFILE
      this.http.post(`${this.baseUrl}/add`, formData)
        .subscribe({
          next: () => {
            alert("Profile saved successfully!");
            this.isEditMode = true;
          },
          error: () => alert("Error saving profile")
        });

    }
  }

  // ---------------- CANCEL ----------------

  onCancel(): void {

    const studentId = localStorage.getItem('studentId');
    const collegeEmail = localStorage.getItem('collegeEmail');

    this.academicForm.reset();

    this.academicForm.patchValue({
      studentId: studentId,
      collegeEmail: collegeEmail
    });

    this.selectedFile = null;
  }
}