package com.decerto.typer.solution.schedule;

import com.decerto.typer.solution.RoundDto;
import lombok.Value;

import java.util.List;

@Value
public class ScheduleDto {

    private List<RoundDto> rounds;
}
