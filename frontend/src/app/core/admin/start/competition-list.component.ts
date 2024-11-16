import {Component, OnInit} from '@angular/core';
import {Tournament, TournamentFull} from "../../model/tournament";
import {MatAnchor, MatButton} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {TournamentRestService} from "../rest/tournament.rest.service";
import {MatCard, MatCardContent} from "@angular/material/card";
import {Router} from "@angular/router";
import {MatchCreatorComponent} from "../../../shared/components/match-creator/match-creator.component";
import {
  MatchCreatorConfig,
  MatchCreatorConfigProvider
} from "../../../shared/components/match-creator/match-creator.config.provider";

@Component({
  selector: 'app-competition-list',
  standalone: true,
  imports: [
    MatButton,
    NgForOf,
    MatAnchor,
    MatCard,
    MatCardContent,
    MatchCreatorComponent,
    NgIf
  ],
  providers: [MatchCreatorConfigProvider],
  templateUrl: 'competition-list.component.html'
})

export class CompetitionListComponent implements OnInit {

  public tournaments: Tournament[] = [];
  public matchCreatorConfig: MatchCreatorConfig;
  public formVisible: boolean;

  constructor(private tournamentRestService: TournamentRestService,
              private router: Router,
              private matchCreatorConfigProvider: MatchCreatorConfigProvider) {
    this.matchCreatorConfig = matchCreatorConfigProvider.getConfig();
    this.formVisible = false;
  }

  ngOnInit() {
    this.tournamentRestService.readTournaments()
      .subscribe(tournaments => this.tournaments = tournaments);
  }

  startNewTournament() {

  }

  reviewTournament(tournament: Tournament) {

  }

  goBack() {
    this.router.navigate(['/admin-dashboard']);
  }

  logout() {
    this.router.navigate(['/logout']);
  }

  selectedTournament(tournament: Tournament) {
    this.tournamentRestService.readTournament(tournament.id)
      .subscribe((tournamentFull) => {
        this.formVisible = true;
        this.matchCreatorConfig?.selectedTournament.next(tournamentFull);
      })
  }
}
