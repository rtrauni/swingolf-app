import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { EventbusService } from './../../eventbus.service';
import { TranslateModule } from '@ngx-translate/core';
import { CoursesComponent } from './courses.component';
import { CoursesRoutingModule } from './courses-routing.module';
import { PageHeaderModule } from './../../shared';

@NgModule({
    imports: [
        CommonModule,
        CoursesRoutingModule,
        PageHeaderModule,
        TranslateModule
    ],
    declarations: [CoursesComponent]
})
export class CoursesModule { }
