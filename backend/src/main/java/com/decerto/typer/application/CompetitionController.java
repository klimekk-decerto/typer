package com.decerto.typer.application;

import com.decerto.typer.application.requests.AssignRoundToMatchRequest;
import com.decerto.typer.application.requests.CreateLeagueCompetitionRequest;
import com.decerto.typer.application.requests.CreateTournamentCompetitionRequest;
import com.decerto.typer.competition.Competition;
import com.decerto.typer.solution.CompetitionDto;
import com.decerto.typer.solution.FooCompetitionFacade;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/completion")
public class CompetitionController {

    private final FooCompetitionFacade competitionFacade;

    public CompetitionController(FooCompetitionFacade competitionFacade) {
        this.competitionFacade = competitionFacade;
    }

    @PostMapping("league")
    public CompetitionDto createLeagueCompetition(@RequestBody CreateLeagueCompetitionRequest request) {
        return competitionFacade.createLeague(request.getLeagueName(), request.getTeamNames());
    }

    @PutMapping("league/assign-round")
    public CompetitionDto assignRoundToLeague(@RequestBody AssignRoundToMatchRequest request) {
        return competitionFacade.chooseRoundForMatch(request);
    }

//    @PostMapping("tournament")
//    public Competition createTournamentCompetition(@RequestBody CreateTournamentCompetitionRequest request) {
//        return competitionFacade.createTournamentCompetition(request.getTournamentName(), request.getGroupTeams());
//    }


}
