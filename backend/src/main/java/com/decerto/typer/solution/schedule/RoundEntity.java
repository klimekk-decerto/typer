package com.decerto.typer.solution.schedule;

import jakarta.persistence.Column;
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
class RoundEntity {

    @GeneratedValue
    @Id
    private Long id;
    @Column(name = "order_of_round")
    private int order;


    public RoundDto toDto() {
        return new RoundDto(id, order);
    }
}
