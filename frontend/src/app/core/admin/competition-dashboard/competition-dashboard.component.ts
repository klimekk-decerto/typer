import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormsModule, ReactiveFormsModule} from "@angular/forms";
import {MatCard, MatCardContent} from "@angular/material/card";
import {ActivatedRoute, Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import {CommonModule} from "@angular/common";
import {MatActionList, MatList} from "@angular/material/list";
import {CompetitionModel} from "../../model/competition-model";
import {MatDivider} from "@angular/material/divider";
import {MatchResultComponent} from "./match-result.component";

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
        MatList,
        MatDivider,
        MatchResultComponent,

    ],
    styleUrls: ['competition-dashboard.component.scss'],
    templateUrl: 'competition-dashboard.component.html'
})
export class CompetitionDashboardComponent implements OnInit {
    public competitionModel: CompetitionModel | null;

    constructor(private router: Router,
                private route: ActivatedRoute,
                private fb: FormBuilder,
                private httpClient: HttpClient) {
        this.competitionModel = null
    }

    getTeamById(id: number) {
        return this.competitionModel?.teams.find(team => team.id === id)?.name;
    }

    ngOnInit() {
        let id = this.route.snapshot.params['id'];
        this.httpClient.get<CompetitionModel>('/app/api/fetch/competition/' + id).subscribe(values => {
            this.competitionModel = values;
        })
    }

  logout() {
    this.router.navigate(['/logout']);
  }

  goBack() {
    this.router.navigate(['/dashboard']);
  }
    predicate(body: any) {
        let id = this.route.snapshot.params['id'];
        this.httpClient.put('/app/api/fetch/competition/' + id + '/matches/' + body.matchId + '/predicate', {scoreA: body.scoreA, scoreB: body.scoreB}).subscribe();
    }
}
