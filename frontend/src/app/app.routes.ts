import {Routes} from '@angular/router';
import {LoginComponent} from "./core/auth/login.component";
import {BlankComponent} from "./core/layout/blank/blank.component";
import {MainPageComponent} from "./core/admin/main-page/main-page.component";

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
    ],
  }
];

