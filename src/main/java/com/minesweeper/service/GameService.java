package com.minesweeper.service;

import com.minesweeper.dto.*;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class GameService {

    private final Map<String, Game> games = new ConcurrentHashMap<>();

    public GameState createGame(GameRequest request) {
        String gameId = UUID.randomUUID().toString();

        int rows, cols, mines;
        switch (request.getDifficulte()) {
            case "Débutant":
                rows = 9; cols = 9; mines = 10;
                break;
            case "Intermédiaire":
                rows = 16; cols = 16; mines = 40;
                break;
            case "Expert":
                rows = 16; cols = 30; mines = 99;
                break;
            case "Personnalisé":
                rows = request.getRows();
                cols = request.getCols();
                mines = Math.min(request.getMines(), rows * cols - 1);
                break;
            default:
                rows = 9; cols = 9; mines = 10;
        }

        Game game = new Game(gameId, rows, cols, mines);
        games.put(gameId, game);

        return game.getState();
    }

    public GameState revealCell(String gameId, int row, int col) {
        Game game = games.get(gameId);
        if (game == null) throw new RuntimeException("Partie introuvable");

        game.revealCell(row, col);
        return game.getState();
    }

    public GameState toggleFlag(String gameId, int row, int col) {
        Game game = games.get(gameId);
        if (game == null) throw new RuntimeException("Partie introuvable");

        game.toggleFlag(row, col);
        return game.getState();
    }

    public void deleteGame(String gameId) {
        games.remove(gameId);
    }

    // Classe interne pour gérer une partie
    private static class Game {
        private final String id;
        private final int rows;
        private final int cols;
        private final int mineCount;
        private final Cell[][] cells;
        private boolean started = false;
        private boolean gameOver = false;
        private boolean won = false;
        private int revealedCount = 0;
        private int flags = 0;

        public Game(String id, int rows, int cols, int mineCount) {
            this.id = id;
            this.rows = rows;
            this.cols = cols;
            this.mineCount = mineCount;
            this.cells = new Cell[rows][cols];

            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    cells[r][c] = new Cell();
                }
            }
        }

        public void revealCell(int row, int col) {
            if (gameOver || cells[row][col].revealed || cells[row][col].flagged) return;

            if (!started) {
                placeMines(row, col);
                started = true;
            }

            Cell cell = cells[row][col];
            cell.revealed = true;
            revealedCount++;

            if (cell.isMine) {
                gameOver = true;
                won = false;
                revealAllMines();
                return;
            }

            if (cell.adjacentMines == 0) {
                floodFill(row, col);
            }

            checkWin();
        }

        public void toggleFlag(int row, int col) {
            if (gameOver || cells[row][col].revealed) return;

            Cell cell = cells[row][col];
            cell.flagged = !cell.flagged;
            flags += cell.flagged ? 1 : -1;
        }

        private void placeMines(int avoidRow, int avoidCol) {
            Set<String> forbidden = new HashSet<>();
            for (int dr = -1; dr <= 1; dr++) {
                for (int dc = -1; dc <= 1; dc++) {
                    int r = avoidRow + dr;
                    int c = avoidCol + dc;
                    if (r >= 0 && r < rows && c >= 0 && c < cols) {
                        forbidden.add(r + "," + c);
                    }
                }
            }

            Random rnd = new Random();
            int placed = 0;
            while (placed < mineCount) {
                int r = rnd.nextInt(rows);
                int c = rnd.nextInt(cols);
                String key = r + "," + c;

                if (forbidden.contains(key) || cells[r][c].isMine) continue;

                cells[r][c].isMine = true;
                placed++;
            }

            calculateAdjacentMines();
        }

        private void calculateAdjacentMines() {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (cells[r][c].isMine) continue;

                    int count = 0;
                    for (int dr = -1; dr <= 1; dr++) {
                        for (int dc = -1; dc <= 1; dc++) {
                            int nr = r + dr;
                            int nc = c + dc;
                            if (nr >= 0 && nr < rows && nc >= 0 && nc < cols && cells[nr][nc].isMine) {
                                count++;
                            }
                        }
                    }
                    cells[r][c].adjacentMines = count;
                }
            }
        }

        private void floodFill(int row, int col) {
            Stack<int[]> stack = new Stack<>();
            stack.push(new int[]{row, col});

            while (!stack.isEmpty()) {
                int[] pos = stack.pop();
                int r = pos[0];
                int c = pos[1];

                for (int dr = -1; dr <= 1; dr++) {
                    for (int dc = -1; dc <= 1; dc++) {
                        int nr = r + dr;
                        int nc = c + dc;

                        if (nr >= 0 && nr < rows && nc >= 0 && nc < cols) {
                            Cell neighbor = cells[nr][nc];
                            if (!neighbor.revealed && !neighbor.flagged && !neighbor.isMine) {
                                neighbor.revealed = true;
                                revealedCount++;

                                if (neighbor.adjacentMines == 0) {
                                    stack.push(new int[]{nr, nc});
                                }
                            }
                        }
                    }
                }
            }
        }

        private void checkWin() {
            if (revealedCount == rows * cols - mineCount) {
                gameOver = true;
                won = true;
            }
        }

        private void revealAllMines() {
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (cells[r][c].isMine) {
                        cells[r][c].revealed = true;
                    }
                }
            }
        }

        public GameState getState() {
            CellState[][] boardState = new CellState[rows][cols];
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    Cell cell = cells[r][c];
                    boardState[r][c] = new CellState(
                            cell.revealed,
                            cell.flagged,
                            cell.revealed && cell.isMine,
                            cell.adjacentMines
                    );
                }
            }

            return new GameState(id, rows, cols, mineCount, revealedCount,
                    flags, gameOver, won, 0, boardState);
        }
    }

    private static class Cell {
        boolean isMine = false;
        int adjacentMines = 0;
        boolean revealed = false;
        boolean flagged = false;
    }
}