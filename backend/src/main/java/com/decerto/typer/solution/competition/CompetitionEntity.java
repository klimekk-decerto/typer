package com.decerto.typer.solution.competition;

import com.decerto.typer.solution.CompetitionDto;
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
public class CompetitionEntity {

    @GeneratedValue
    @Id
    private Long id;
    private String name;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "team_id")
    private List<TeamEntity> teams = new ArrayList<>();


    public static CompetitionEntity ofLeague(String name, List<String> names) {
        return new CompetitionEntity(null, name, names.stream()
                .map(teamName -> new TeamEntity(null, teamName))
                .toList());
    }

    public List<TeamDto> toDto() {
        return teams.stream().map(TeamEntity::toDto).toList();
    }
}
