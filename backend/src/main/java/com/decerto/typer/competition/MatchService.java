package com.decerto.typer.competition;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatchService {
    private final MatchRepository matchRepository;
    private final PredictionRepository predictionRepository;
    private final RankingService rankingService;

    public Match setMatchResult(Long matchId, Integer scoreA, Integer scoreB) {
        Match match = matchRepository.findById(matchId).orElseThrow();
        match.setScoreA(scoreA);
        match.setScoreB(scoreB);
        match.setStatus(MatchStatus.COMPLETED);

        matchRepository.save(match);

        // Rozliczanie typowań
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
