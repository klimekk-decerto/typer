package com.decerto.typer.ranking;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRankingRepository extends JpaRepository<UserRanking, Long> {
    List<UserRanking> findAllByOrderByPointsDesc();
    Optional<UserRanking> findByUsername(String username);
}

