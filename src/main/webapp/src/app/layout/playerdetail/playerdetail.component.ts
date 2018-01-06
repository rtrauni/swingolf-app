import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { Subject, Observable, Subscription } from 'rxjs/Rx';
import { EventbusService } from './../../eventbus.service';

@Component({
  selector: 'app-playerdetail',
  templateUrl: './playerdetail.component.html',
  styleUrls: ['./playerdetail.component.scss']
})
export class PlayerdetailComponent implements OnInit {
  private eventBusService: EventbusService;
  private license: String;
  public alerts: Array<any> = [];
  public sliders: Array<any> = [];

  players: Array<any> = ["{firstname:''}"];

  constructor(private route: ActivatedRoute, eventbusService: EventbusService) {
    this.route.params.subscribe( params => {this.license=params['id'];console.log(params);} );
    this.eventBusService = eventbusService;
  }

  ngOnInit() {
    this.eventBusService.getDetailsForUser(this.license).subscribe(players => {
      this.players = players;
      console.log(players);
    });
  }

}
