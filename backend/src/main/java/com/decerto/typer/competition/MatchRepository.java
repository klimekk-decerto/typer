package com.decerto.typer.competition;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<Match, Long> {
    List<Match> findByCompetitionId(Long competitionId);
}

