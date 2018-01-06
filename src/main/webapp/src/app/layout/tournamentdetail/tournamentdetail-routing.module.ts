import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { TournamentdetailComponent } from './tournamentdetail.component';

const routes: Routes = [
    { path: '', component: TournamentdetailComponent }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class TournamentdetailRoutingModule { }
