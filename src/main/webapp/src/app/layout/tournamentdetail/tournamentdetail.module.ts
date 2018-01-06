import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventbusService } from './../../eventbus.service';
import { ChartsModule as Ng2Charts } from 'ng2-charts';

import { TournamentdetailComponent } from './tournamentdetail.component';
import { TournamentdetailRoutingModule } from './tournamentdetail-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        Ng2Charts,
        TournamentdetailRoutingModule,
        PageHeaderModule
    ],
    declarations: [TournamentdetailComponent]
})
export class TournamentdetailModule { }
