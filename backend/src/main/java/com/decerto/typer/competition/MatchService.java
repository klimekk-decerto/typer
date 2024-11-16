package com.decerto.typer.competition;

import com.decerto.typer.prediction.Prediction;
import com.decerto.typer.prediction.PredictionRepository;
import com.decerto.typer.ranking.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final CompetitionRepository competitionRepository;
    private final MatchRepository matchRepository;
    private final PredictionRepository predictionRepository;
    private final RankingService rankingService;

    public Match setMatchResult(Long competitionId, Long matchId, Integer scoreA, Integer scoreB) {
        Competition competition = competitionRepository.findById(competitionId).orElseThrow();
        competition.setMatchResult(matchId, scoreA, scoreB);


        Match match = matchRepository.findById(matchId).orElseThrow();
        List<Prediction> predictions = predictionRepository.findByMatch(match);
        rankingService.processPredictions(predictions, match);
        return match;
    }

    public Match setMatchDate(Long matchId, LocalDateTime dateTime) {
        Match match = matchRepository.findById(matchId).orElseThrow();
        match.setMatchDate(dateTime);

        return match;
    }
}
