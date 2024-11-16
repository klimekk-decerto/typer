package com.decerto.typer;

import com.decerto.typer.schedule.MatchDto;
import com.decerto.typer.schedule.RoundDto;
import lombok.Value;

import java.util.List;

@Value
public class CompetitionDto {
    Long id;

    List<TeamDto> teams;

    List<RoundDto> rounds;

    List<MatchDto> matches;

}
