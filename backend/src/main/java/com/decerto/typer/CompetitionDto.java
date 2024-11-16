package com.decerto.typer;

import com.decerto.typer.prediction.PredicationRank;
import com.decerto.typer.schedule.MatchDto;
import lombok.Value;

import java.util.List;

@Value
public class CompetitionDto {
    Long id;

    List<TeamDto> teams;

    List<MatchDto> matches;

    List<MatchDto> matchesForPredicate;

    PredicationRank predicationRank;

    MatchDto getMatchForce(Long id) {
        return matches.stream()
                .filter(match -> match.getMatchId().equals(id))
                .findFirst()
                .orElseThrow();
    }

}
