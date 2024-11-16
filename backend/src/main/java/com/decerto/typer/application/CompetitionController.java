package com.decerto.typer.application;

import com.decerto.typer.application.requests.CreateMatchRequest;
import com.decerto.typer.application.requests.CreateLeagueCompetitionRequest;
import com.decerto.typer.CompetitionDto;
import com.decerto.typer.CompetitionFacade;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/completion")
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

//    @PostMapping("tournament")
//    public Competition createTournamentCompetition(@RequestBody CreateTournamentCompetitionRequest request) {
//        return competitionFacade.createTournamentCompetition(request.getTournamentName(), request.getGroupTeams());
//    }


}
