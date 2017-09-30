import { Component, OnInit } from '@angular/core';
import { EventbusService } from './../../eventbus.service';
import { EventBus } from 'vertx3-eventbus-rx-client';
import { Subject, Observable, Subscription } from 'rxjs/Rx';
import { routerTransition } from '../../router.animations';

@Component({
  selector: 'app-players',
  templateUrl: './players.component.html',
  styleUrls: ['./players.component.scss'],
  animations: [routerTransition()]
})
export class PlayersComponent implements OnInit {
  private eventBusService: EventbusService;
  players: Observable<Array<any>>;

  constructor(eventbusService: EventbusService){
    this.eventBusService = eventbusService;
  }

  ngOnInit() {
    this.eventBusService.getUsersAndLicense().subscribe(players => {
      this.players = players;
      console.log(this.players);
    });
  }
}
