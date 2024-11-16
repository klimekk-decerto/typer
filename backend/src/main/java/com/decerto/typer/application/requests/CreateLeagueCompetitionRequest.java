package com.decerto.typer.application.requests;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Value;

import java.util.List;

@Value
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class CreateLeagueCompetitionRequest {

    String leagueName;
    List<String> teamNames;

}