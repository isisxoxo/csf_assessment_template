import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppComponent } from './app.component';
import { MainComponent } from './views/main.component';
import { PictureComponent } from './views/picture.component';
import { ReactiveFormsModule } from '@angular/forms';
import { WebcamModule } from 'ngx-webcam';
import { HttpClientModule } from '@angular/common/http';
import { RouterModule, Routes } from '@angular/router';
import { UploadService } from './upload.service';

const appRoutes: Routes = [
  {path: '', component: MainComponent},
  {path: 'picture', component: PictureComponent},
  {path: '**', redirectTo:'/', pathMatch: 'full'}
]


@NgModule({
  declarations: [
    AppComponent, MainComponent, PictureComponent
  ],
  imports: [
    BrowserModule,
    ReactiveFormsModule,
    WebcamModule,
    HttpClientModule,
    RouterModule.forRoot(appRoutes, {useHash: true})
  ],
  providers: [UploadService],
  bootstrap: [AppComponent]
})
export class AppModule { }
