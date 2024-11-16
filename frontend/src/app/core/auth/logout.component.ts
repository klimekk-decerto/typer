import {Component, OnInit} from '@angular/core';
import {UserDataService} from "./user.data.service";
import {Router} from "@angular/router";

@Component({
  selector: 'app-logout',
  standalone: true,
  template: ''
})
export class LogoutComponent implements OnInit {
  constructor(
    private userDataService: UserDataService,
    private router: Router) {
  }

  ngOnInit() {
    this.userDataService.destroyToken();
    this.router.navigate(['/login']);
  }
}
