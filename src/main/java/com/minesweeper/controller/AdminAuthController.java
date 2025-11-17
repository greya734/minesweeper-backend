package com.minesweeper.controller;

import com.minesweeper.dto.LoginRequest;
import com.minesweeper.dto.LoginResponse;
import com.minesweeper.security.JwtUtil;
import com.minesweeper.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminAuthController {

    @Autowired
    private AdminUserService adminUserService;

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        try {
            // Vérifier les identifiants
            boolean authenticated = adminUserService.authenticate(
                loginRequest.getUsername(), 
                loginRequest.getPassword()
            );

            if (authenticated) {
                // Générer le token JWT
                String token = jwtUtil.generateToken(loginRequest.getUsername());
                
                LoginResponse response = new LoginResponse(
                    token,
                    loginRequest.getUsername(),
                    "Connexion réussie"
                );
                
                return ResponseEntity.ok(response);
            } else {
                Map<String, String> error = new HashMap<>();
                error.put("message", "Nom d'utilisateur ou mot de passe incorrect");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error);
            }
        } catch (Exception e) {
            Map<String, String> error = new HashMap<>();
            error.put("message", "Erreur lors de la connexion");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        // Le logout est géré côté client en supprimant le token
        // Ici, on peut éventuellement blacklister le token
        Map<String, String> response = new HashMap<>();
        response.put("message", "Déconnexion réussie");
        return ResponseEntity.ok(response);
    }
}
