import { Component } from '@angular/core';
import {IoddService} from "./service/iodd.service";
import {CommandService} from "./service/command.service";
import {OnInit} from '@angular/core';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.sass']
})
export class AppComponent implements OnInit{

  STATUS_READY = 'Ready';
  STATUS_RECEIVING = 'Receiving ...';

  numberIodd: number;
  numberRev0: number;
  numberRev1: number;
  numberCom1: number;
  numberCom2: number;
  numberCom3: number;

  status: string;
  socket: string;
  rest: string;

  iodds: any[];

  constructor(
    private iodd: IoddService,
    private command: CommandService
  ){}

  ngOnInit(): void {
    this.iodd.refreshIodds();

    this.iodd.getIoddObserver().subscribe(iodds => this.onNewIodds(iodds));
    this.iodd.getStatusObserver().subscribe(status => this.onNewStatus(status));

    this.status = this.STATUS_READY;
  }

  public manualRefresh(){
    this.status = this.STATUS_RECEIVING;
    this.iodd.refreshIodds();
  };

  private onNewStatus(status: string){
    this.status = this.STATUS_READY;
    this.rest = status;
  }

  private onNewIodds(iodds: any[]){
    this.status = this.STATUS_READY;
    this.iodds = iodds;
    this.recalculateStatistics();
  }

  private recalculateStatistics(){
    this.numberIodd = this.iodds.length;
    this.numberRev0 = this.iodds.filter(x => x.io_link_revision == 'V1.0').length;
    this.numberRev1 = this.iodds.filter(x => x.io_link_revision == 'V1.1').length;
    this.numberCom1 = this.iodds.filter(x => x.bitrate == 'COM1').length;
    this.numberCom2 = this.iodds.filter(x => x.bitrate == 'COM2').length;
    this.numberCom3 = this.iodds.filter(x => x.bitrate == 'COM3').length;
  }

}
