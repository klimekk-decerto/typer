package com.decerto.typer.competition;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class CompetitionFacade {
    private final CompetitionService competitionService;
    private final MatchService matchService;
    private final PredictionService predictionService;
    private final RankingService rankingService;

    public CompetitionFacade(CompetitionService competitionService,
                             MatchService matchService,
                             PredictionService predictionService,
                             RankingService rankingService) {
        this.competitionService = competitionService;
        this.matchService = matchService;
        this.predictionService = predictionService;
        this.rankingService = rankingService;
    }

    public Competition createLeagueCompetition(String name, List<String> teamNames) {
        return competitionService.createLeagueCompetition(name, teamNames);
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

    public Prediction placePrediction(String username, Long matchId, Integer scoreA, Integer scoreB, boolean isDraw) {
        return predictionService.placePrediction(username, matchId, scoreA, scoreB, isDraw);
    }

    public List<UserRanking> getRanking() {
        return rankingService.getRanking();
    }

    public Match setMatchResult(Long matchId, Integer scoreA, Integer scoreB) {
        return matchService.setMatchResult(matchId, scoreA, scoreB);
    }
}


