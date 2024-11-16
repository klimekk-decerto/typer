package com.decerto.typer.schedule;

import lombok.Value;

import java.util.List;
import java.util.Set;

@Value
public class ScheduleDto {

    private List<MatchDto> matches;

    public List<MatchDto> findMatches(Set<Long> matches) {
        return this.matches.stream()
                .filter(match -> matches.contains(match.getMatchId()))
                .toList();
    }
}
