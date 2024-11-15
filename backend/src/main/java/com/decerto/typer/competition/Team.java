package com.decerto.typer.competition;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String groupName;
    @ManyToOne
    @JoinColumn(name = "competition_id")
    private Competition competition;


}

