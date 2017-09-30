import { Component, OnInit } from '@angular/core';
import { EventbusService } from './../../eventbus.service';
import { Subject, Observable, Subscription } from 'rxjs/Rx';
import { routerTransition } from '../../router.animations';

@Component({
  selector: 'app-courses',
  templateUrl: './courses.component.html',
  styleUrls: ['./courses.component.scss'],
  animations: [routerTransition()]
})
export class CoursesComponent implements OnInit {
  private eventBusService: EventbusService;
  players: Observable<Array<any>>;

  constructor(eventbusService: EventbusService){
    this.eventBusService = eventbusService;
  }

  ngOnInit() {
    this.eventBusService.getCourses().subscribe(courses => {
      this.courses = courses;
      console.log(this.courses);
    });
  }

}
