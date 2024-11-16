import {Routes} from '@angular/router';
import {LoginComponent} from "./core/auth/login.component";
import {BlankComponent} from "./core/layout/blank/blank.component";
import {MainPageComponent} from "./core/admin/main-page/main-page.component";
import {CompetitionListComponent} from "./core/admin/start/competition-list.component";
import {Dashboard} from "./core/admin/dashboard/dashboard.component";
import {CompetitionDashboardComponent} from "./core/admin/competition-dashboard/competition-dashboard.component";

export const routes: Routes = [
  // {path: '', component: BlankComponent},
  {
    path: '', component: BlankComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
      {
        path: 'admin-dashboard',
        component: MainPageComponent
      },
      {
        path: 'dashboard',
        component: Dashboard,
      },
      {
        path: 'dashboard/:id',
        component: CompetitionDashboardComponent,
      },
      {
        path: 'admin-dashboard-competitions',
        component: CompetitionListComponent
      }
    ],
  }
];

