package com.decerto.typer.competition;

import com.decerto.typer.TeamDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

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
                .map(teamName -> new TeamEntity(null, teamName, "Grupa ligowa"))
                .toList());
    }

    public static CompetitionEntity ofTournament(String name, Map<String, List<String>> groups) {
        return new CompetitionEntity(null, name, groups.entrySet().stream()
                .map(entry -> entry.getValue().stream()
                        .map(teamName -> new TeamEntity(null, teamName, entry.getKey()))
                        .toList())
                .flatMap(Collection::stream)
                .toList());

    }

    public List<TeamDto> toDto() {
        return teams.stream().map(TeamEntity::toDto).toList();
    }
}
