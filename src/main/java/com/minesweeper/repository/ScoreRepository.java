package com.minesweeper.repository;

import com.minesweeper.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Long> {

    @Query(value = "SELECT * FROM score WHERE difficulte = :difficulte " +
            "ORDER BY temps ASC LIMIT 4",
            nativeQuery = true)
    List<Score> findTop4ByDifficulteOrderByTempsAsc(@Param("difficulte") String difficulte);

    List<Score> findByDifficulteOrderByTempsAsc(String difficulte);
}