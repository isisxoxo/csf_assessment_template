import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { firstValueFrom } from "rxjs";
import { dataToImage } from "./utils";

@Injectable()
export class UploadService {

  imageData = ""

  constructor(private http: HttpClient) {
  }

  // TODO: Task 3.
  // You may add add parameters to the method
  upload(form: any): Promise<any> {

    const formData = new FormData();
    formData.set("title", form['title']);
    formData.set("comments", form['commenets']);
    formData.set("date", (new Date()).toISOString());
    formData.set("picture", dataToImage(this.imageData));
    
    return firstValueFrom(this.http.post<any>("/api/image/upload", formData));
  }
}
