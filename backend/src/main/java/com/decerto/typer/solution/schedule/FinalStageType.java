package com.decerto.typer.solution.schedule;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum FinalStageType {
    ONE_EIGHT(3), ONE_FOUR(2);
    private final int requiredRounds;
}
