import {Routes} from '@angular/router';
import {MainPageComponent} from "./feature/appointments/pages/main/main.page.component";
import {LoginComponent} from "./core/auth/login.component";
import {BlankComponent} from "./core/layout/blank/blank.component";

export const routes: Routes = [
  // {path: '', component: BlankComponent},
  {
    path: '', component: BlankComponent,
    children: [
      {
        path: 'login',
        component: LoginComponent
      },
    ],
  }
];

