package com.decerto.typer.schedule;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class MatchDto {
    Long matchId;
    Long firstTeamId;
    Long secondTeamId;
    int firstTeamScore;
    int secondTeamScore;
    LocalDateTime date;
}
