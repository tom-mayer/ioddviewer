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
    private ioddService: IoddService,
    private commandService: CommandService
  ){}

  ngOnInit(): void {
    this.ioddService.refreshIodds();

    this.ioddService.getIoddObserver().subscribe(iodds => this.onNewIodds(iodds));
    this.ioddService.getStatusObserver().subscribe(status => this.onNewStatus(status));
    this.commandService.getCommandObserver().subscribe( command => this.onCommandReveived(command));
    this.commandService.getStatusObserver().subscribe(status => this.onNewSocketStatus(status));

    this.status = this.STATUS_READY;
  }

  public manualRefresh(){
    this.status = this.STATUS_RECEIVING;
    this.ioddService.refreshIodds();
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

  private onCommandReveived(command: string){
    if(command == 'REFRESH'){
      this.ioddService.refreshIodds();
    }
  }

  private onNewSocketStatus(status: string){
    this.socket = status;
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
