import { Component, OnInit } from '@angular/core';
import { EventbusService } from './../../eventbus.service';
import { Subject, Observable, Subscription } from 'rxjs/Rx';
import { routerTransition } from '../../router.animations';

@Component({
  selector: 'app-clubs',
  templateUrl: './clubs.component.html',
  styleUrls: ['./clubs.component.scss'],
  animations: [routerTransition()]
})
export class ClubsComponent implements OnInit {
  private eventBusService: EventbusService;
  clubs: Observable<Array<any>>;

  constructor(eventbusService: EventbusService){
    this.eventBusService = eventbusService;
  }

  ngOnInit() {
    this.eventBusService.getClubs().subscribe(clubs => {
      this.clubs = clubs;
      console.log(this.clubs);
    });
  }

}
