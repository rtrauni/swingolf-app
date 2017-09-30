import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { PlayersComponent } from './players.component';
import { PlayersRoutingModule } from './players-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        PlayersRoutingModule,
        PageHeaderModule
    ],
    declarations: [PlayersComponent]
})
export class PlayersModule { }
