import { Component, OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { routerTransition } from '../../router.animations';
import { EventbusService } from './../../eventbus.service';
import { Subject, Observable, Subscription } from 'rxjs/Rx';

@Component({
    selector: 'app-dashboard',
    templateUrl: './dashboard.component.html',
    styleUrls: ['./dashboard.component.scss'],
    animations: [routerTransition()]
})
export class DashboardComponent implements OnInit {
    public alerts: Array<any> = [];
    public sliders: Array<any> = [];

    private eventBusService: EventbusService;
    tournaments: Observable<Array<any>>;

  constructor(eventbusService: EventbusService){
    this.eventBusService = eventbusService;











        this.sliders.push({
            imagePath: 'assets/images/IMG_1726.jpg',
            label: 'Marco Bortoli',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1729.jpg',
            label: 'Joe Senf',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1736.jpg',
            label: 'Thomas Langer',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1737.jpg',
            label: 'Stephan Kronsdorfer',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1742.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1745.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1748.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1749.jpg',
            label: 'Franz',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1748.jpg',
            label: 'Erwin',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1750.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1751.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1753.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1756.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1757.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1761.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1763.jpg',
            label: '',
            text: ''
        }, {
            imagePath: 'assets/images/IMG_1765.jpg',
            label: '',
            text: ''
        });

        this.alerts.push({
            id: 1,
            type: 'success',
            message: `Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Voluptates est animi quibusdam praesentium quam, et perspiciatis,
                consectetur velit culpa molestias dignissimos
                voluptatum veritatis quod aliquam! Rerum placeat necessitatibus, vitae dolorum`
        }, {
            id: 2,
            type: 'warning',
            message: `Lorem ipsum dolor sit amet, consectetur adipisicing elit.
                Voluptates est animi quibusdam praesentium quam, et perspiciatis,
                consectetur velit culpa molestias dignissimos
                voluptatum veritatis quod aliquam! Rerum placeat necessitatibus, vitae dolorum`
        });
    }

    ngOnInit() {

    }

    public closeAlert(alert: any) {
        const index: number = this.alerts.indexOf(alert);
        this.alerts.splice(index, 1);
    }
}
