import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {environment} from '../../environments/environment';
import { Subject, Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class IoddService {

  STATUS_OK = 'API ok';
  STATUS_BROKEN = 'API error';

  private baseUrl: string;
  private iodds = new Subject<any>();
  private status = new Subject<string>();

  constructor(
    private http: HttpClient
  ){
    this.baseUrl = environment.apiUrl;
  }

  getIoddObserver(): Observable<any[]>{
    return this.iodds.asObservable();
  }

  getStatusObserver(): Observable<string>{
    return this.status.asObservable();
  }

  refreshIodds(){
    const url = `${this.baseUrl}/iodds`;
    const headers = new HttpHeaders();
    this.http.get<any[]>(url).toPromise<any[]>()
      .then((res) => {
        this.status.next(this.STATUS_OK);
        this.iodds.next(res);
      }).catch((err) => {
      this.status.next(this.STATUS_BROKEN);
      console.log("error: " + err);
    });
  }
}
