package com.minesweeper.controller;

import com.minesweeper.dto.*;
import com.minesweeper.service.GameService;
import com.minesweeper.service.ScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import java.util.Map;

@RestController
@RequestMapping("/api")
@Validated
@CrossOrigin(origins = "*")
public class MinesweeperController {

    private final ScoreService scoreService;
    private final GameService gameService;

    @Autowired
    public MinesweeperController(ScoreService scoreService, GameService gameService) {
        this.scoreService = scoreService;
        this.gameService = gameService;
    }

    // ========== Endpoints Scores ==========

    @PostMapping("/scores")
    public ResponseEntity<ScoreResponse> saveScore(@Valid @RequestBody ScoreRequest request) {
        ScoreResponse response = scoreService.saveScore(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scores/top4/{difficulte}")
    public ResponseEntity<Top4Response> getTop4(@PathVariable String difficulte) {
        Top4Response response = scoreService.getTop4(difficulte);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/scores/{difficulte}")
    public ResponseEntity<?> getAllScores(@PathVariable String difficulte) {
        return ResponseEntity.ok(scoreService.getAllScoresByDifficulty(difficulte));
    }

    // ========== Endpoints Jeu ==========

    @PostMapping("/game/new")
    public ResponseEntity<GameState> createGame(@Valid @RequestBody GameRequest request) {
        GameState state = gameService.createGame(request);
        return ResponseEntity.ok(state);
    }

    @PostMapping("/game/{gameId}/reveal")
    public ResponseEntity<GameState> revealCell(
            @PathVariable String gameId,
            @RequestBody Map<String, Integer> coords) {

        int row = coords.get("row");
        int col = coords.get("col");
        GameState state = gameService.revealCell(gameId, row, col);
        return ResponseEntity.ok(state);
    }

    @PostMapping("/game/{gameId}/flag")
    public ResponseEntity<GameState> toggleFlag(
            @PathVariable String gameId,
            @RequestBody Map<String, Integer> coords) {

        int row = coords.get("row");
        int col = coords.get("col");
        GameState state = gameService.toggleFlag(gameId, row, col);
        return ResponseEntity.ok(state);
    }

    @DeleteMapping("/game/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable String gameId) {
        gameService.deleteGame(gameId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        return ResponseEntity.ok(Map.of(
                "status", "OK",
                "message", "Minesweeper API is running"
        ));
    }
}