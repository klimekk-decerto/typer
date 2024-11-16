import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCard, MatCardContent} from "@angular/material/card";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {Tournament} from "../../model/tournament";
import {CommonModule} from "@angular/common";
import {MatActionList, MatList, MatListItem} from "@angular/material/list";

@Component({
    selector: 'app-dashboard',
    standalone: true,
    imports: [
        FormsModule,
        MatCard,
        MatCardContent,
        ReactiveFormsModule,
        CommonModule,
        MatActionList,
        MatListItem,
        MatList,

    ],
    styleUrls: ['dashboard.component.scss'],
    templateUrl: 'dashboard.component.html'
})
export class Dashboard implements OnInit {
    public tournaments: Tournament[] = [];

    constructor(private router: Router,
                private httpClient: HttpClient) {
        this.tournaments = [{
            name: 'Foo',
            id: 1,
        },
            {
                name: 'Bla',
                id: 2,
            }];
    }

    ngOnInit() {
        // this.httpClient.get<Tournament[]>('/app/api/fetch/competitions').subscribe(values => {
        //     this.tournaments = values;
        // })
    }


    goToTournaments(id: number) {
        this.router.navigate(['/dashboard/' + id]);
    }

  logout() {
    this.router.navigate(['/logout']);
  }
}
