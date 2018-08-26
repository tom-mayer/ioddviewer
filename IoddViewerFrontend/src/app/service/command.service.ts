import { Injectable } from '@angular/core';
import {Subject, Observable} from 'rxjs';
import {environment} from '../../environments/environment';
import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';

@Injectable({
  providedIn: 'root'
})
export class CommandService {

  STATUS_OK = 'Connected';
  STATUS_BROKEN = 'Disconnected';

  private status = new Subject<string>();
  private command = new Subject<string>();
  private baseUrl: string;

  private stompClient;

  constructor() {
    this.baseUrl = environment.socketUrl;
    this.status.next(this.STATUS_BROKEN);
    this.initialize();
  }

  public getStatusObserver(): Observable<string>{
    return this.status.asObservable();
  }

  public getCommandObserver(): Observable<string>{
    return this.command.asObservable();
  }

  public initialize(){
    let ws = new SockJS(`${this.baseUrl}`);
    this.stompClient = Stomp.over(ws);
    let that = this;
    this.stompClient.connect({}, (frame) => {
      that.status.next(that.STATUS_OK);
      that.stompClient.subscribe("/commands", (message) => {
        if(message.body) {
          that.command.next(message.body);
        }
      });
    });
  }
}
