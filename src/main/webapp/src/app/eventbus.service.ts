import { Injectable } from '@angular/core';
import { Subject, Observer, Observable } from 'rxjs/Rx';
import { EventBus } from 'vertx3-eventbus-rx-client';

@Injectable()
export class EventbusService {
  eventBus: EventBus;
  constructor() {
    this.eventBus = EventBus.create("https://db.swingolf.at/eventbus");
    //this.eventBus = EventBus.create("http://localhost:8088/eventbus");
  }

  getUsers(): Observable<any> {
    return this.eventBus.rxSend('users', 'query').map(message => message.body);
  }

  getUsersTest(): Observable<any> {
    return this.eventBus.rxSend('userstest', 'query').map(message => message.body);
  }

  getDetailsForUser(license: String) {
    return this.eventBus.rxSend('getDetailsForUser', license).map(message => message.body);
  }

  getDetailsForTournament(tournamentId: String) {
    return this.eventBus.rxSend('getDetailsForTournament', tournamentId).map(message => message.body);
  }

  getCourses(): Observable<any> {
    return this.eventBus.rxSend('courses', 'query').map(message => message.body);
  }

  getClubs(): Observable<any> {
    return this.eventBus.rxSend('clubs', 'query').map(message => message.body);
  }

  getUsersAndLicense(): Observable<any> {
    return this.eventBus.rxSend('users-and-license', 'query').map(message => message.body);
  }

  getActiveUsersAndLicense(): Observable<any> {
    return this.eventBus.rxSend('active-users-and-license', 'query').map(message => message.body);
  }

  getTournamentsAndDates(): Observable<any> {
    return this.eventBus.rxSend('tournaments-and-dates', 'query').map(message => message.body);
  }

  getPrevious3Tournaments(): Observable<any> {
    return this.eventBus.rxSend('tournaments-previous3', 'query').map(message => message.body);
  }

  getPrevious3TournamentsByUser(license: String): Observable<any> {
    return this.eventBus.rxSend('tournaments-previous3-by-user', license).map(message => message.body);
  }

  getThisYearTournamentsByUser(license: String): Observable<any> {
    return this.eventBus.rxSend('tournaments-this-year-by-user', license).map(message => message.body);
  }

  getAllTournamentsByUser(license: String): Observable<any> {
    return this.eventBus.rxSend('tournaments-all-by-user', license).map(message => message.body);
  }

  getNext3Tournaments(): Observable<any> {
    return this.eventBus.rxSend('tournaments-next3', 'query').map(message => message.body);
  }

  getPlayerCountForTournament(tournamentId: String): Observable<any> {
                                                                           return this.eventBus.rxSend('getPlayerCountForTournament', tournamentId).map(message => message.body);
                                                                         }

  getUsersByTournament(tournamentId: String): Observable<any> {
    return this.eventBus.rxSend('users-by-tournament', tournamentId).map(message => message.body);
  }

  getScoreSorted(tournamentId: String): Observable<any> {
                        return this.eventBus.rxSend('score-sorted', tournamentId).map(message => message.body);
                      }

  getScoreSortedCount(tournamentId: String): Observable<any> {
                        return this.eventBus.rxSend('score-sorted-count', tournamentId).map(message => message.body);
                      }

}
