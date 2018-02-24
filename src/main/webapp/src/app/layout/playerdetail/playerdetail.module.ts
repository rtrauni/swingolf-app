import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventbusService } from './../../eventbus.service';
import { TranslateModule } from '@ngx-translate/core';
import { PlayerdetailComponent } from './playerdetail.component';
import { PlayerdetailRoutingModule } from './playerdetail-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        PlayerdetailRoutingModule,
        PageHeaderModule,
        TranslateModule
    ],
    declarations: [PlayerdetailComponent]
})
export class PlayerdetailModule { }
