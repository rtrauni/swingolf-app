import { Injectable } from '@angular/core';
import { Subject, Observer, Observable } from 'rxjs/Rx';
import { EventBus } from 'vertx3-eventbus-rx-client';
import { State } from 'vertx3-eventbus-rx-client';

@Injectable()
export class EventbusService {
  eventBus: EventBus;
  constructor() {
    this.init();
  }

  init(): void {
    console.log('eventBus: ' + this.eventBus);
    if (this.eventBus == null || this.eventBus.state == State.CLOSED) {
      if (this.eventBus != null) {
        console.log('eventBus.state: ' + this.eventBus.state);
      }
      this.eventBus = EventBus.create("https://db.swingolf.at/eventbus");
      console.log('eventBus after creation: ' + this.eventBus);
      this.eventBus.state$.subscribe(
        state => {
          console.log('eventBus.state after state change: ' + this.eventBus.state);
          if (state!== State.CLOSED) {
            console.log(this.eventBus.closeEvent); // null
            this.init();
          } else {
            console.log(this.eventBus.closeEvent); // NOT null. Refer to CloseEvent docs on the link below.
            this.init();
          }
        }
      );
      if (this.eventBus.state != State.OPEN) {
        console.log('eventBus.state before sleep: ' + this.eventBus.state);
        this.sleep(2000);
        console.log('eventBus.state after sleep: ' + this.eventBus.state);
      }
    }
    //this.eventBus = EventBus.create("http://localhost:8088/eventbus");
  }

  getUsers(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('users', 'query').map(message => message.body);
  }

  getUsersTest(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('userstest', 'query').map(message => message.body);
  }

  getDetailsForUser(license: String) {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('getDetailsForUser', license).map(message => message.body);
  }

  getDetailsForUser2(license: String) {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('user-by-license', license).map(message => message.body);
  }

  getDetailsForTournament(tournamentId: String) {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('getDetailsForTournament', tournamentId).map(message => message.body);
  }

  getBestScoreThisYear(tournamentId: String) {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('best-score-this-year-by-player', tournamentId).map(message => message.body);
  }

  getBestScore(tournamentId: String) {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('best-score-by-player', tournamentId).map(message => message.body);
  }

  getCourses(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('courses', 'query').map(message => message.body);
  }

  getClubs(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('clubs', 'query').map(message => message.body);
  }

  getUsersAndLicense(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('users-and-license', 'query').map(message => message.body);
  }

  getActiveUsersAndLicense(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('active-users-and-license', 'query').map(message => message.body);
  }

  getTournamentsAndDates(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('tournaments-and-dates', 'query').map(message => message.body);
  }

  getPrevious3Tournaments(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('tournaments-previous3', 'query').map(message => message.body);
  }

  getPrevious3TournamentsByUser(license: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('tournaments-previous3-by-user', license).map(message => message.body);
  }

  getThisYearTournamentsByUser(license: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('tournaments-this-year-by-user', license).map(message => message.body);
  }

  getAllTournamentsByUser(license: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('tournaments-all-by-user', license).map(message => message.body);
  }

  getNext3Tournaments(): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('tournaments-next3', 'query').map(message => message.body);
  }

  getPlayerCountForTournament(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
                                                                           return this.eventBus.rxSend('getPlayerCountForTournament', tournamentId).map(message => message.body);
                                                                         }

  getUsersByTournamentSortedByScore(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
    return this.eventBus.rxSend('users-by-tournament-sorted-by-score', tournamentId).map(message => message.body);
  }

  getScoreSorted(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
                        return this.eventBus.rxSend('score-sorted', tournamentId).map(message => message.body);
                      }

  getScoreSortedCount(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
                        return this.eventBus.rxSend('score-sorted-count', tournamentId).map(message => message.body);
                      }

  getBestScore1(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
                        return this.eventBus.rxSend('best-score-by-tournament', tournamentId).map(message => message.body);
                      }

  getAverageScore(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
                        return this.eventBus.rxSend('average-score-by-tournament', tournamentId).map(message => message.body);
                      }

  getBestTrack(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
                        return this.eventBus.rxSend('best-track-by-tournament', tournamentId).map(message => message.body);
                      }

  getAverageTrack(tournamentId: String): Observable<any> {
    this.init();
    if (this.eventBus.state!= State.OPEN) {
      return Observable.empty();
    }
                        return this.eventBus.rxSend('average-track-by-tournament', tournamentId).map(message => message.body);
                      }

  sleep(milliseconds) {
    var start = new Date().getTime();
    for (var i = 0; i < 1e7; i++) {
      if ((new Date().getTime() - start) > milliseconds){
        break;
      }
    }
  }
}
