import { Component, OnInit } from '@angular/core';
import { Params, Router } from '@angular/router';
import { UploadService } from '../upload.service';
import { Observable, Subject } from 'rxjs';
import { WebcamImage } from 'ngx-webcam';

@Component({
  selector: 'app-main',
  templateUrl: './main.component.html',
  styleUrl: './main.component.css'
})
export class MainComponent implements OnInit {

  width = 500
  height = 282

  trigger = new Subject<void>()

  // ratio$!: Observable<Params>

  // TODO: Task 1

  constructor(private router: Router, private uploadSvc: UploadService) {
  }

  ngOnInit(): void {
    this.width = 500
    this.width = 282
  }
  
  changeRatio(event: any) {
    this.height = event.target.value
    this.width = 500
    console.log(">>>>> HEIGHT:", this.height) //TO REMOVE
    console.log(">>>>> WIDTH:", this.width) //TO REMOVE
  }

  takePicture() {
    this.trigger.next()
  }

  snapshot(webcamImg: WebcamImage) {
    this.uploadSvc.imageData = webcamImg.imageAsDataUrl
    this.router.navigate(['/picture'])
  }

}
