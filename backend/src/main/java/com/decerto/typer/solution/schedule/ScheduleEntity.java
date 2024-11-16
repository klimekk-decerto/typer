package com.decerto.typer.solution.schedule;

import com.decerto.typer.solution.TeamDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
class ScheduleEntity {

    @GeneratedValue
    @Id
    private Long id;
    private Long competitionId;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "round_id")
    private List<RoundEntity> rounds;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "round_id")
    private List<MatchEntity> matches;

    public static ScheduleEntity ofLeagueSchedule(Long competitionId, List<TeamDto> teams) {
        List<RoundEntity> rounds = new ArrayList<>();
        int numberOfRounds = 2 * (teams.size() - 1);
        for (int i = 1; i <= numberOfRounds; i++) {
            rounds.add(new RoundEntity(null, i));
        }
        List<MatchEntity> matches = new ArrayList<>();
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < teams.size(); j++) {
                if (i != j) {
                    MatchEntity match = new MatchEntity(null, teams.get(i).id(), teams.get(j).id());
                    matches.add(match);
                }
            }
        }
        return new ScheduleEntity(null, competitionId, rounds, matches);
    }

    public ScheduleDto toDto() {
        return new ScheduleDto(
                rounds.stream().map(RoundEntity::toDto).toList(),
                matches.stream().map(MatchEntity::toDto).toList()
        );
    }
}
