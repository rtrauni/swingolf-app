import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventbusService } from './../../eventbus.service';
import { TranslateModule } from '@ngx-translate/core';
import { TournamentsComponent } from './tournaments.component';
import { TournamentsRoutingModule } from './tournaments-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        TournamentsRoutingModule,
        TranslateModule,
        PageHeaderModule
    ],
    declarations: [TournamentsComponent]
})
export class TournamentsModule { }
