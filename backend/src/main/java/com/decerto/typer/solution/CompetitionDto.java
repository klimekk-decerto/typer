package com.decerto.typer.solution;

import com.decerto.typer.solution.schedule.MatchDto;
import com.decerto.typer.solution.schedule.RoundDto;
import lombok.Value;

import java.util.List;

@Value
public class CompetitionDto {
    Long id;

    List<TeamDto> teams;

    List<RoundDto> rounds;

    List<MatchDto> matches;

}
