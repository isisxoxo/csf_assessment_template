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
    this.saveForm$ = this.uploadSvc.upload(this.form)
                    .then(
                      data => this.router.navigate(['/'])
                    )
                    .catch(
                      error => alert(error)
                    )
  }
}
