import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PlayerdetailComponent } from './playerdetail.component';

const routes: Routes = [
    { path: '', component: PlayerdetailComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PlayerdetailRoutingModule { }
