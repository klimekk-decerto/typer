package com.decerto.typer.solution;

import lombok.Value;

import java.util.List;

@Value
public class CompetitionDto {

    List<TeamDto> teams;

    List<RoundDto> rounds;
}
