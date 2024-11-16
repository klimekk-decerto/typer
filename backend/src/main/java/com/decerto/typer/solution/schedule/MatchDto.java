package com.decerto.typer.solution.schedule;

import lombok.Value;

@Value
public class MatchDto {
    Long matchId;
    Long firstTeamId;
    Long secondTeamId;
}
