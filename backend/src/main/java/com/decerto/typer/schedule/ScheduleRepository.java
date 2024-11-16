package com.decerto.typer.schedule;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity, Long> {
    ScheduleEntity findByCompetitionId(Long id);
}
