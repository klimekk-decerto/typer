package com.decerto.typer;

import com.decerto.typer.schedule.MatchDto;
import lombok.Value;

import java.util.List;

@Value
public class CompetitionDto {
    Long id;

    List<TeamDto> teams;


    List<MatchDto> matches;

}
