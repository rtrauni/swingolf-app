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
  public license: String;
  public bestScoreThisYear: String;
  public bestScore: String;
  public playerURL: String = "https://db.swingolf.at/assets/images/players/noimage.jpg";
  public alerts: Array<any> = [];
  public sliders: Array<any> = [];
  previoustournaments: Observable<Array<any>>;
  thisyeartournaments: Observable<Array<any>>;
  alltournaments: Observable<Array<any>>;
  thisyeartournamentslength: number = 0;
  alltournamentslength: number= 0;

  players: Array<any> = ["{firstname:''}"];
  players2: Array<any> = ["{firstname:''}"];

  constructor(private route: ActivatedRoute, eventbusService: EventbusService) {
    this.route.params.subscribe( params => {this.license=params['id'];this.playerURL="https://db.swingolf.at/assets/images/players/"+this.license+".jpg";console.log(params);} );
    this.eventBusService = eventbusService;
  }

  ngOnInit() {
    this.eventBusService.getBestScoreThisYear(this.license).subscribe(bestScore => {
      this.bestScoreThisYear = bestScore;
      this.bestScoreThisYear = this.bestScoreThisYear === null ? "-" : bestScore;
      console.log(bestScore);
    });
    this.eventBusService.getBestScore(this.license).subscribe(bestScore => {
      this.bestScore = bestScore;
      this.bestScore = this.bestScore === null ? "-" : bestScore;
      console.log(bestScore);
    });
    this.eventBusService.getDetailsForUser(this.license).subscribe(players => {
      this.players = players;
      console.log(players);
    });
    this.eventBusService.getDetailsForUser2(this.license).subscribe(players2 => {
      this.players2 = players2;
      console.log(players2);
    });
    this.eventBusService.getPrevious3TournamentsByUser(this.license).subscribe(tournaments => {
       this.previoustournaments = tournaments;
       console.log(this.previoustournaments);
    });
    this.eventBusService.getAllTournamentsByUser(this.license).subscribe(tournaments => {
       this.alltournaments = tournaments;
       this.alltournamentslength= tournaments.length;
       console.log(this.alltournaments);
    });
    this.eventBusService.getThisYearTournamentsByUser(this.license).subscribe(tournaments => {
       this.thisyeartournaments = tournaments;
       this.thisyeartournamentslength= tournaments.length;
       console.log(this.thisyeartournaments);
    });

  }

}
