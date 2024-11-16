package com.decerto.typer.solution.schedule;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MatchDto {
    Long matchId;
    Long firstTeamId;
    Long secondTeamId;
    Long roundId;
    int firstTeamScore;
    int secondTeamScore;
    LocalDateTime date;
}
