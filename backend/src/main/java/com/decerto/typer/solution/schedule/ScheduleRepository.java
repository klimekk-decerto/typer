package com.decerto.typer.solution.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    ScheduleEntity findByCompetitionId(Long id);
}
