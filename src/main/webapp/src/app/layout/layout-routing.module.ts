import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

const routes: Routes = [
    {
        path: '', component: LayoutComponent,
        children: [
            { path: 'dashboard', loadChildren: './dashboard/dashboard.module#DashboardModule' },
            { path: 'charts', loadChildren: './charts/charts.module#ChartsModule' },
            { path: 'tables', loadChildren: './tables/tables.module#TablesModule' },
            { path: 'players', loadChildren: './players/players.module#PlayersModule' },
            { path: 'playerdetail/:id', loadChildren: './playerdetail/playerdetail.module#PlayerdetailModule' },
            { path: 'tournamentdetail/:id', loadChildren: './tournamentdetail/tournamentdetail.module#TournamentdetailModule' },
            { path: 'courses', loadChildren: './courses/courses.module#CoursesModule' },
            { path: 'clubs', loadChildren: './clubs/clubs.module#ClubsModule' },
            { path: 'tournaments', loadChildren: './tournaments/tournaments.module#TournamentsModule' },
            { path: 'forms', loadChildren: './form/form.module#FormModule' },
            { path: 'bs-element', loadChildren: './bs-element/bs-element.module#BsElementModule' },
            { path: 'grid', loadChildren: './grid/grid.module#GridModule' },
            { path: 'components', loadChildren: './bs-component/bs-component.module#BsComponentModule' },
            { path: 'blank-page', loadChildren: './blank-page/blank-page.module#BlankPageModule' },
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class LayoutRoutingModule { }
