import { Component } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { EventbusService } from './eventbus.service';
@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
    constructor(private translate: TranslateService, eventbusService: EventbusService) {
        translate.addLangs(['de','en', 'fr', 'ur', 'es', 'it', 'fa']);
        translate.setDefaultLang('de');
        const browserLang = translate.getBrowserLang();
        translate.use(browserLang.match(/de|en|fr|ur|es|it|fa/) ? browserLang : 'en');
    }

}
