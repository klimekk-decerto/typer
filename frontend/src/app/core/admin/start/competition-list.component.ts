import {Component, OnInit} from '@angular/core';
import {Tournament} from "../../model/tournament";
import {MatAnchor, MatButton} from "@angular/material/button";
import {NgForOf} from "@angular/common";
import {TournamentRestService} from "../rest/tournament.rest.service";
import {MatCard, MatCardContent} from "@angular/material/card";
import {Router} from "@angular/router";

@Component({
  selector: 'app-competition-list',
  standalone: true,
  imports: [
    MatButton,
    NgForOf,
    MatAnchor,
    MatCard,
    MatCardContent
  ],
  templateUrl: 'competition-list.component.html'
})

export class CompetitionListComponent implements OnInit {

  public tournaments: Tournament[] = [];

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
}
