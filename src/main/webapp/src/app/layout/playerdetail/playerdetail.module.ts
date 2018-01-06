import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventbusService } from './../../eventbus.service';

import { PlayerdetailComponent } from './playerdetail.component';
import { PlayerdetailRoutingModule } from './playerdetail-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        PlayerdetailRoutingModule,
        PageHeaderModule
    ],
    declarations: [PlayerdetailComponent]
})
export class PlayerdetailModule { }
