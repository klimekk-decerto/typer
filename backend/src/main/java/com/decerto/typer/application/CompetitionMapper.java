package com.decerto.typer.application;

import com.decerto.typer.schedule.*;
import com.decerto.typer.CompetitionDto;
import com.decerto.typer.TeamDto;
import com.decerto.typer.competition.CompetitionEntity;
import com.decerto.typer.competition.TeamEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CompetitionMapper {

    public CompetitionDto map(CompetitionEntity competition, ScheduleEntity schedule) {
        List<TeamDto> teams = mapTeams(competition.getTeams());

        List<RoundDto> rounds = mapRounds(schedule.getRounds());

        List<MatchDto> matches = mapMatches(schedule.getMatches());
        return new CompetitionDto(competition.getId(), teams, rounds, matches);
    }

    private List<MatchDto> mapMatches(List<MatchEntity> matches) {
        return matches.stream()
                .map(MatchEntity::toDto)
                .toList();
    }

    private List<RoundDto> mapRounds(List<RoundEntity> rounds) {
        return rounds.stream()
                .map(RoundEntity::toDto)
                .toList();
    }

    private List<TeamDto> mapTeams(List<TeamEntity> teams) {
        return teams.stream()
                .map(TeamEntity::toDto)
                .toList();
    }

}
