-- Script de création de la base de données Minesweeper

CREATE DATABASE IF NOT EXISTS Minesweeper 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE Minesweeper;

DROP TABLE IF EXISTS score;
DROP VIEW IF EXISTS meilleurs_scores;

CREATE TABLE score (
    id INT AUTO_INCREMENT PRIMARY KEY,
    pseudo VARCHAR(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    difficulte VARCHAR(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL,
    temps INT NOT NULL,
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_difficulte (difficulte),
    INDEX idx_temps (temps),
    INDEX idx_difficulte_temps (difficulte, temps)
) ENGINE=InnoDB 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

CREATE OR REPLACE VIEW meilleurs_scores AS
SELECT 
    id,
    pseudo,
    difficulte,
    temps,
    DATE_FORMAT(date, '%d/%m/%Y à %H:%i') as date_formatted,
    date,
    ROW_NUMBER() OVER (PARTITION BY difficulte ORDER BY temps ASC, date ASC) as rang
FROM score
ORDER BY difficulte, temps ASC;