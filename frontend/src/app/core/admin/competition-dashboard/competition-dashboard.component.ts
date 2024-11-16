import {Component, OnInit} from '@angular/core';
import {FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCard, MatCardContent} from "@angular/material/card";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {MatActionList, MatList, MatListItem, MatListItemTitle} from "@angular/material/list";
import {CompetitionModel} from "../../model/competition-model";

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
    MatListItemTitle,

  ],
  styleUrls: ['competition-dashboard.component.scss'],
  templateUrl: 'competition-dashboard.component.html'
})
export class CompetitionDashboardComponent implements OnInit {
  public competitionModel: CompetitionModel;

  constructor(private router: Router,
              private route: ActivatedRoute,
              private httpClient: HttpClient) {
    this.competitionModel = {
      name: 'Test',
      id: 1,
      matches: [{
        firstTeamId: 1,
        secondTeamId: 2,
        firstTeamScore: 5,
        secondTeamScore: 7,
        date: '2020-10-10',
        matchId: 0
      },
        {
          firstTeamId: 1,
          secondTeamId: 2,
          firstTeamScore: 5,
          secondTeamScore: 7,
          date: '2020-10-11',
          matchId: 0
        }
      ]
    }
  }

  ngOnInit() {
    // this.httpClient.get<CompetitionModel>('/app/api/fetch/competitions/' + this.route.snapshot.params.get('id')).subscribe(values => {
    //     this.competitionModel = values;
    // })
  }



}
