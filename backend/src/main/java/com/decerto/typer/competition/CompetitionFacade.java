package com.decerto.typer.competition;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Transactional
@Service
public class CompetitionFacade {
    private final CompetitionService competitionService;
    private final MatchService matchService;

    public CompetitionFacade(CompetitionService competitionService,
                             MatchService matchService) {
        this.competitionService = competitionService;
        this.matchService = matchService;
    }

    public Competition createLeagueCompetition(String name, List<String> teamNames) {
        return competitionService.createLeagueCompetition(name, teamNames);
    }

    public List<Match> getMatches(Long competitionId) {
        return matchService.findByCompetitionId(competitionId);
    }

    public Competition createTournamentCompetition(String name, Map<String, List<String>> groupTeams) {
        return competitionService.createTournamentCompetition(name, groupTeams);
    }

    public List<Match> setupTournamentBracket(Long competitionId, List<TournamentMatch> bracketMatches) {
        return competitionService.setupTournamentBracket(competitionId, bracketMatches);
    }

    public Match setMatchDate(Long matchId, LocalDateTime dateTime) {
        return matchService.setMatchDate(matchId, dateTime);
    }

    public void beginCompetition(Long competitionId) {
        competitionService.beginCompetition(competitionId);
    }

    public Match setMatchResult(Long competitionId, Long matchId, Integer scoreA, Integer scoreB) {
        return matchService.setMatchResult(competitionId, matchId, scoreA, scoreB);
    }
}


