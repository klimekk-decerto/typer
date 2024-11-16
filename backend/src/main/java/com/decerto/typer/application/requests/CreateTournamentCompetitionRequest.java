package com.decerto.typer.application.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;
import java.util.Map;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CreateTournamentCompetitionRequest {

    String tournamentName;
    Map<String, List<String>> groupTeams;

}
