package com.decerto.typer.application.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class AssignRoundToMatchRequest {
    long competitionId;
    long roundId;
    long firstTeamId;
    long secondTeamId;
    LocalDateTime date;
}
