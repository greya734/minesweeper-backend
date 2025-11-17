package com.minesweeper.service;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AdminUserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Base de données d'utilisateurs en mémoire
    // En production, utiliser une vraie base de données
    private final Map<String, String> users = new HashMap<>();

    /**
     * Initialiser l'utilisateur admin par défaut au démarrage
     * IMPORTANT : Utiliser @PostConstruct et NON @Bean !
     */
    @PostConstruct
    public void initializeDefaultUser() {
        // Utilisateur par défaut : admin / admin123
        users.put("admin", passwordEncoder.encode("admin123"));
        System.out.println("✅ Utilisateur admin initialisé avec succès");
    }

    /**
     * Authentifier un utilisateur
     */
    public boolean authenticate(String username, String password) {
        String storedPassword = users.get(username);
        if (storedPassword == null) {
            return false;
        }
        return passwordEncoder.matches(password, storedPassword);
    }

    /**
     * Vérifier si un utilisateur existe
     */
    public boolean userExists(String username) {
        return users.containsKey(username);
    }

    /**
     * Ajouter un nouvel utilisateur admin
     */
    public void addUser(String username, String password) {
        users.put(username, passwordEncoder.encode(password));
        System.out.println("✅ Nouvel utilisateur ajouté : " + username);
    }

    /**
     * Supprimer un utilisateur
     */
    public void removeUser(String username) {
        if (users.remove(username) != null) {
            System.out.println("✅ Utilisateur supprimé : " + username);
        }
    }

    /**
     * Obtenir le nombre d'utilisateurs
     */
    public int getUserCount() {
        return users.size();
    }
}
