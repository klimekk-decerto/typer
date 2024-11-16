package com.decerto.typer.schedule;

import lombok.Value;

import java.util.List;

@Value
public class ScheduleDto {

    private List<MatchDto> matches;
}
