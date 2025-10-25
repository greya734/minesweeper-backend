package com.minesweeper;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MinesweeperApplication {

    public static void main(String[] args) {
        SpringApplication.run(MinesweeperApplication.class, args);
        System.out.println("💣 Minesweeper API démarrée sur http://localhost:8080");
        System.out.println("📖 Interface web: http://localhost:8080");
    }
}