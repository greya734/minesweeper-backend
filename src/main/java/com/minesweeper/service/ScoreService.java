package com.minesweeper.service;

import com.minesweeper.dto.*;
import com.minesweeper.entity.Score;
import com.minesweeper.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class ScoreService {

    private final ScoreRepository scoreRepository;
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");

    @Autowired
    public ScoreService(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    @Transactional
    public ScoreResponse saveScore(ScoreRequest request) {
        Score score = new Score();
        score.setPseudo(request.getPseudo());
        score.setDifficulte(request.getDifficulte());
        score.setTemps(request.getTemps());

        Score saved = scoreRepository.save(score);

        return toResponse(saved, getRank(saved));
    }

    public Top4Response getTop4(String difficulte) {
        List<Score> scores = scoreRepository.findTop4ByDifficulteOrderByTempsAsc(difficulte);

        AtomicInteger rank = new AtomicInteger(1);
        List<ScoreResponse> responses = scores.stream()
                .map(s -> toResponse(s, rank.getAndIncrement()))
                .collect(Collectors.toList());

        return new Top4Response(difficulte, responses);
    }

    public List<ScoreResponse> getAllScoresByDifficulty(String difficulte) {
        List<Score> scores = scoreRepository.findByDifficulteOrderByTempsAsc(difficulte);

        AtomicInteger rank = new AtomicInteger(1);
        return scores.stream()
                .map(s -> toResponse(s, rank.getAndIncrement()))
                .collect(Collectors.toList());
    }

    private int getRank(Score score) {
        List<Score> betterScores = scoreRepository.findByDifficulteOrderByTempsAsc(score.getDifficulte())
                .stream()
                .filter(s -> s.getTemps() < score.getTemps() ||
                        (s.getTemps().equals(score.getTemps()) && s.getId() < score.getId()))
                .collect(Collectors.toList());

        return betterScores.size() + 1;
    }

    private ScoreResponse toResponse(Score score, int rang) {
        return new ScoreResponse(
                score.getId(),
                score.getPseudo(),
                score.getDifficulte(),
                score.getTemps(),
                score.getDate().format(FORMATTER),
                rang
        );
    }
}