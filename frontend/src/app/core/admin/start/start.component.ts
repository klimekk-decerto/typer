import {Component, OnInit} from '@angular/core';
import {Tournament} from "../../model/tournament";
import {MatButton} from "@angular/material/button";
import {NgForOf} from "@angular/common";
import {TournamentRestService} from "../rest/tournament.rest.service";

@Component({
  selector: 'app-start',
  standalone: true,
  imports: [
    MatButton,
    NgForOf
  ],
  templateUrl: 'start.component.html'
})

export class StartComponent implements OnInit {

  public tournaments: Tournament[] = [];

  constructor(private  tournamentRestService: TournamentRestService) {
  }

  ngOnInit() {
    this.tournamentRestService.readTournaments()
        .subscribe(tournaments => this.tournaments = tournaments);
  }

  startNewTournament() {

  }

  reviewTournament(tournament: Tournament) {

  }
}
