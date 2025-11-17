package com.minesweeper.controller;

import com.minesweeper.dto.ScoreRequest;
import com.minesweeper.dto.ScoreResponse;
import com.minesweeper.entity.Score;
import com.minesweeper.repository.ScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    
    private final ScoreRepository scoreRepository;
    private static final DateTimeFormatter FORMATTER = 
        DateTimeFormatter.ofPattern("dd/MM/yyyy 'à' HH:mm");
    
    @Autowired
    public AdminController(ScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }
    
    // Récupérer tous les scores
    @GetMapping("/scores")
    public ResponseEntity<List<ScoreResponse>> getAllScores() {
        List<Score> scores = scoreRepository.findAll();
        List<ScoreResponse> response = scores.stream()
            .map(this::toResponse)
            .collect(Collectors.toList());
        return ResponseEntity.ok(response);
    }
    
    // Récupérer un score par ID
    @GetMapping("/scores/{id}")
    public ResponseEntity<ScoreResponse> getScoreById(@PathVariable Long id) {
        return scoreRepository.findById(id)
            .map(score -> ResponseEntity.ok(toResponse(score)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Créer un score
    @PostMapping("/scores")
    public ResponseEntity<ScoreResponse> createScore(@RequestBody ScoreRequest request) {
        Score score = new Score();
        score.setPseudo(request.getPseudo());
        score.setDifficulte(request.getDifficulte());
        score.setTemps(request.getTemps());
        score.setDate(LocalDateTime.now());
        
        Score saved = scoreRepository.save(score);
        return ResponseEntity.ok(toResponse(saved));
    }
    
    // Modifier un score
    @PutMapping("/scores/{id}")
    public ResponseEntity<ScoreResponse> updateScore(
            @PathVariable Long id, 
            @RequestBody ScoreRequest request) {
        
        return scoreRepository.findById(id)
            .map(score -> {
                score.setPseudo(request.getPseudo());
                score.setDifficulte(request.getDifficulte());
                score.setTemps(request.getTemps());
                Score updated = scoreRepository.save(score);
                return ResponseEntity.ok(toResponse(updated));
            })
            .orElse(ResponseEntity.notFound().build());
    }
    
    // Supprimer un score
    @DeleteMapping("/scores/{id}")
    public ResponseEntity<Void> deleteScore(@PathVariable Long id) {
        if (scoreRepository.existsById(id)) {
            scoreRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    
    // Supprimer tous les scores d'une difficulté
    @DeleteMapping("/scores/difficulty/{difficulte}")
    public ResponseEntity<Void> deleteByDifficulty(@PathVariable String difficulte) {
        List<Score> scores = scoreRepository.findByDifficulteOrderByTempsAsc(difficulte);
        scoreRepository.deleteAll(scores);
        return ResponseEntity.noContent().build();
    }
    
    // Supprimer tous les scores
    @DeleteMapping("/scores")
    public ResponseEntity<Void> deleteAllScores() {
        scoreRepository.deleteAll();
        return ResponseEntity.noContent().build();
    }
    
    // Statistiques
    @GetMapping("/stats")
    public ResponseEntity<?> getStats() {
        long total = scoreRepository.count();
        List<Object[]> statsByDiff = scoreRepository.findStatsByDifficulty();
        
        return ResponseEntity.ok(new java.util.HashMap<String, Object>() {{
            put("total", total);
            put("byDifficulty", statsByDiff);
        }});
    }
    
    private ScoreResponse toResponse(Score score) {
        return new ScoreResponse(
            score.getId(),
            score.getPseudo(),
            score.getDifficulte(),
            score.getTemps(),
            score.getDate().format(FORMATTER),
            0
        );
    }
}
