import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventbusService } from './../../eventbus.service';

import { ClubsComponent } from './clubs.component';
import { ClubsRoutingModule } from './clubs-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        ClubsRoutingModule,
        PageHeaderModule
    ],
    declarations: [ClubsComponent]
})
export class ClubsModule { }
