import { Injectable } from '@angular/core';
import { Subject, Observer, Observable } from 'rxjs/Rx';
import { EventBus } from 'vertx3-eventbus-rx-client';

@Injectable()
export class EventbusService {
  eventBus: EventBus;
  constructor() {
    this.eventBus = EventBus.create("http://localhost:8080/eventbus");
  }

  getUsers(): Observable<any> {
    return this.eventBus.rxSend('users', 'query').map(message => message.body);
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

}
