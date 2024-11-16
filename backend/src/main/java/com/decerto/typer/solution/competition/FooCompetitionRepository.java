package com.decerto.typer.solution.competition;

import org.springframework.data.jpa.repository.JpaRepository;

interface FooCompetitionRepository extends JpaRepository<CompetitionEntity, Long> {
}
