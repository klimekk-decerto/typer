package com.decerto.typer.solution.competition;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TeamEntity {
    @GeneratedValue
    @Id
    private Long id;
    private String name;

    public TeamDto toDto() {
        return new TeamDto(id, name, null);
    }
}
