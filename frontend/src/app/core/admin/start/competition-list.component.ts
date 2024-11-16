import {Component, OnInit} from '@angular/core';
import {Tournament, TournamentFull} from "../../model/tournament";
import {MatAnchor, MatButton} from "@angular/material/button";
import {NgForOf, NgIf} from "@angular/common";
import {TournamentRestService} from "../rest/tournament.rest.service";
import {MatCard, MatCardContent} from "@angular/material/card";
import {Router} from "@angular/router";
import {MatchCreatorComponent} from "../../../shared/components/match-creator/match-creator.component";

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
  templateUrl: 'competition-list.component.html'
})

export class CompetitionListComponent implements OnInit {

  public tournaments: Tournament[] = [];
  public activeTournament: TournamentFull | null = null;

  constructor(private tournamentRestService: TournamentRestService, private router: Router) {
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
      .subscribe((tournamentFull) => this.activeTournament = tournamentFull)
  }
}
