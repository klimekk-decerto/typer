package com.decerto.typer.application;

import com.decerto.typer.CompetitionDto;
import com.decerto.typer.CompetitionFacade;
import com.decerto.typer.application.requests.CreateLeagueCompetitionRequest;
import com.decerto.typer.application.requests.CreateMatchRequest;
import com.decerto.typer.application.requests.CreateTournamentCompetitionRequest;
import com.decerto.typer.application.requests.ScoreRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/competition")
public class CompetitionController {

    private final CompetitionFacade competitionFacade;

    public CompetitionController(CompetitionFacade competitionFacade) {
        this.competitionFacade = competitionFacade;
    }

    @PostMapping("league")
    public CompetitionDto createLeagueCompetition(@RequestBody CreateLeagueCompetitionRequest request) {
        return competitionFacade.createLeague(request.getLeagueName(), request.getTeamNames());
    }

    @PutMapping("league/assign-round")
    public CompetitionDto createMatch(@RequestBody CreateMatchRequest request) {
        return competitionFacade.createMatch(request);
    }

    @DeleteMapping("{id}/matches/{matchId}")
    public CompetitionDto deleteMatch(@PathVariable Long id, @PathVariable Long matchId) {
        return competitionFacade.deleteMatch(id, matchId);
    }

    @PostMapping("tournament")
    public CompetitionDto createTournamentCompetition(@RequestBody CreateTournamentCompetitionRequest request) {
        return competitionFacade.createTournament(request.getTournamentName(), request.getGroupTeams());
    }

    @PutMapping("{id}/matches/{matchId}/finish")
    public CompetitionDto finishMatch(@PathVariable Long id, @PathVariable Long matchId, @RequestBody ScoreRequest request) {
        return competitionFacade.finishMatch(id, matchId, request.getScoreA(), request.getScoreB());
    }

    @PutMapping("{id}/matches/{matchId}/predicate")
    public CompetitionDto predicate(@PathVariable Long id, @PathVariable Long matchId, @RequestBody ScoreRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentPrincipalName = authentication.getName();
        return competitionFacade.predicate(currentPrincipalName, id, matchId, request.getScoreA(), request.getScoreB());
    }
}
