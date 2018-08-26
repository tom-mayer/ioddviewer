import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppComponent } from './app.component';
import {CommandService} from "./service/command.service";
import {IoddService} from "./service/iodd.service";

import {HttpClientModule} from "@angular/common/http";

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    BrowserModule,
    HttpClientModule
  ],
  providers: [
    CommandService,
    IoddService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
