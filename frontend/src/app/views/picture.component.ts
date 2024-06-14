import { Component, OnInit } from '@angular/core';
import { UploadService } from '../upload.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-picture',
  templateUrl: './picture.component.html',
  styleUrl: './picture.component.css'
})
export class PictureComponent implements OnInit {
  
  imageData!: string
  form!: FormGroup
  saveForm$!: Promise<any>

  constructor(private uploadSvc: UploadService, private fb: FormBuilder, private router: Router) {
  }

  // TODO: Task 2
  // TODO: Task 3

  ngOnInit(): void {
    this.imageData = this.uploadSvc.imageData

    this.form = this.fb.group({
      title: this.fb.control('', [Validators.required, Validators.minLength(5)]),
      comments: this.fb.control(''),
      webcamImg: this.imageData
    })
  }

  back() {
    if(confirm("Are you sure you want to discard image?")) {
      this.router.navigate(['/'])
    }
  }

  submit() {

    const formData = new FormData();
    formData.set("title", this.form.get('title')?.value);
    formData.set("comments", this.form.get('comments')?.value);
    formData.set("date", (new Date()).toISOString());
    
    this.saveForm$ = this.uploadSvc.upload(formData)
                    .then(
                      data => this.router.navigate(['/'])
                    )
                    .catch(
                      error => {
                        alert(error.error.message)
                      }
                    )
  }
}
