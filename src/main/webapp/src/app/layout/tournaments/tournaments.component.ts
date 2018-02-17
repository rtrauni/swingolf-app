import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { EventbusService } from './../../eventbus.service';
import { Subject, Observable, Subscription } from 'rxjs/Rx';
import { routerTransition } from '../../router.animations';


@Component({
  selector: 'app-tournaments',
  templateUrl: './tournaments.component.html',
  styleUrls: ['./tournaments.component.scss'],
  animations: [routerTransition()]
})
export class TournamentsComponent implements OnInit {
  private eventBusService: EventbusService;
  tournaments: Observable<Array<any>>;
  previoustournaments: Observable<Array<any>>;
  nexttournaments: Observable<Array<any>>;

  constructor(eventbusService: EventbusService){
    this.eventBusService = eventbusService;
  }


  ngOnInit() {
    this.eventBusService.getTournamentsAndDates().subscribe(tournaments => {
      this.tournaments = tournaments;
      console.log(this.tournaments);
    });
        this.eventBusService.getPrevious3Tournaments().subscribe(tournaments => {
            this.previoustournaments = tournaments;
            console.log(this.tournaments);
        });
        this.eventBusService.getNext3Tournaments().subscribe(tournaments => {
            this.nexttournaments = tournaments;
            console.log(this.tournaments);
        });
  }

}
