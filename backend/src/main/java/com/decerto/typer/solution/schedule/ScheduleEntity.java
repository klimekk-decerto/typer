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

    public static ScheduleEntity ofLeagueSchedule(Long competitionId, List<TeamDto> teams) {
        List<RoundEntity> rounds = new ArrayList<>();
        for (int i=1;i<teams.size();i++) {
            rounds.add(new RoundEntity(null, i));
        }
        return new ScheduleEntity(null, competitionId, rounds);
    }

    public ScheduleDto toDto() {
        return new ScheduleDto(rounds.stream().map(RoundEntity::toDto).toList());
    }
}
