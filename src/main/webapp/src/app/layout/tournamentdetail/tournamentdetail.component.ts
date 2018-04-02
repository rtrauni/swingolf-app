import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from "@angular/router";
import { Subject, Observable, Subscription } from 'rxjs/Rx';
import { EventbusService } from './../../eventbus.service';

@Component({
  selector: 'app-tournamentdetail',
  templateUrl: './tournamentdetail.component.html',
  styleUrls: ['./tournamentdetail.component.scss']
})
export class TournamentdetailComponent implements OnInit {
  private eventBusService: EventbusService;
  public tournamentId: String;
  public bestTrack: String;
  public averageTrack: String;
  public bestScore: String;
  public averageScore: String;

  public playerCount: String;

  players: Observable<Array<any>>;
  tournaments: Array<any> = ["{name:''}"];
      public barChartOptions: any = {
          scaleShowVerticalLines: false,
          responsive: true
      };
      public barChartType: string = 'bar';
      public barChartLegend: boolean = false;

      public barChartLabels: string[] = ['2006', '2007', '2008', '2009', '2010', '2011', '2012'];
      public barChartData: any[] = [
          { data: [65, 59, 80, 81, 56, 55, 40], label: 'Series A' }
      ];

    // Pie
    public pieChartLabels: string[] = ['Download Sales', 'In-Store Sales', 'Mail Sales'];
    public pieChartData: number[] = [300, 500, 100];
    public pieChartType: string = 'pie';

  constructor(private route: ActivatedRoute, eventbusService: EventbusService) {
    this.route.params.subscribe( params => {this.tournamentId=params['id'];console.log(params);} );
    this.eventBusService = eventbusService;
  }

  ngOnInit() {
    this.eventBusService.getDetailsForTournament(this.tournamentId).subscribe(tournaments => {
      this.tournaments = tournaments;
      console.log(tournaments);
    });

    this.eventBusService.getScoreSorted(this.tournamentId).subscribe(scoreSorted => {
      this.barChartLabels = scoreSorted;
      this.pieChartLabels = scoreSorted;
      console.log(scoreSorted);
    });

    this.eventBusService.getScoreSortedCount(this.tournamentId).subscribe(scoreSortedCount => {
      this.barChartData = scoreSortedCount;
      this.pieChartData = scoreSortedCount;
      console.log(scoreSortedCount);
    });

        this.eventBusService.getPlayerCountForTournament(this.tournamentId).subscribe(scoreSortedCount => {
          this.playerCount = scoreSortedCount;
          console.log(scoreSortedCount);
        });

    this.eventBusService.getUsersByTournamentSortedByScore(this.tournamentId).subscribe(players => {
      this.players= players;
      console.log(players);
    });

    this.eventBusService.getBestScore1(this.tournamentId).subscribe(bestScore => {
      this.bestScore= bestScore;
      console.log(bestScore);
    });


    this.eventBusService.getAverageScore(this.tournamentId).subscribe(averageScore => {
      this.averageScore= averageScore;
      console.log(averageScore);
    });

    this.eventBusService.getBestTrack(this.tournamentId).subscribe(bestTrack => {
      this.bestTrack= bestTrack;
      console.log(bestTrack);
    });

    this.eventBusService.getAverageTrack(this.tournamentId).subscribe(averageTrack => {
      this.averageTrack= averageTrack;
      console.log(averageTrack);
    });

  }
}
