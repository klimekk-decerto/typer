package com.decerto.typer.solution.schedule;

import lombok.Value;

import java.util.List;

@Value
public class ScheduleDto {

    private List<RoundDto> rounds;
    private List<MatchDto> matches;
}
