package com.decerto.typer.application.requests;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.time.LocalDateTime;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Builder
public class CreateMatchRequest {
    long competitionId;
    long firstTeamId;
    long secondTeamId;
    LocalDateTime date;
}
