package com.decerto.typer.prediction;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PredicationRank {

    List<Participant> participants;

    public PredicationRank() {
        this.participants = new ArrayList<>();
    }

    public void addPredicationPoints(String name, int points) {
        participants.stream()
                .filter(participant -> participant.getName().equals(name))
                .findFirst()
                .ifPresentOrElse(
                        participant -> participant.addPoints(points),
                        () -> {
                            Participant participant = new Participant(name, points);
                            participants.add(participant);
                        });
    }

}
