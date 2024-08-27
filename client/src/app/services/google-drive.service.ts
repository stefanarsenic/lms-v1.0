import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class GoogleDriveService {
  private uploadUrl = 'http://localhost:8080/api/drive/upload';
  private listFilesUrl = 'http://localhost:8080/api/drive/files';

  constructor(private http: HttpClient) { }

  uploadFile(file: File) {
    const formData: any = new FormData();
    formData.append('file', file, file.name);
    return this.http.post(this.uploadUrl, formData, { responseType: 'json' });
  }

  listFiles() {
    return this.http.get(this.listFilesUrl);
  }
}
