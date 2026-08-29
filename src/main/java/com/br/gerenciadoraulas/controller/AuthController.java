package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.config.JwtTokenProvider;
import com.br.gerenciadoraulas.model.Usuario;
import com.br.gerenciadoraulas.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsuarioRepository usuarioRepository;

    public AuthController(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        String usernameOrEmail = credentials.get("usernameOrEmail");
        String password = credentials.get("password");

        if (usernameOrEmail == null || password == null) {
            return ResponseEntity.badRequest().body(Map.of("error", "Usuário/Email e senha são obrigatórios"));
        }

        Optional<Usuario> userOpt = usuarioRepository.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuário ou senha incorretos"));
        }

        Usuario usuario = userOpt.get();
        if (!BCrypt.checkpw(password, usuario.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Usuário ou senha incorretos"));
        }

        String token = JwtTokenProvider.generateToken(usuario.getUsername(), usuario.getAdmin());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "username", usuario.getUsername(),
                "email", usuario.getEmail(),
                "admin", usuario.getAdmin(),
                "theme", usuario.getTheme(),
                "nome", usuario.getNome() != null ? usuario.getNome() : usuario.getUsername()
        ));
    }

    @PutMapping("/theme")
    public ResponseEntity<?> updateTheme(@RequestBody Map<String, String> body, HttpServletRequest request) {
        String theme = body.get("theme");
        if (theme == null || theme.trim().isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Tema é obrigatório"));
        }

        String username = (String) request.getAttribute("username");
        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Não autorizado"));
        }

        Optional<Usuario> userOpt = usuarioRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "Usuário não encontrado"));
        }

        Usuario usuario = userOpt.get();
        usuario.setTheme(theme.trim());
        usuarioRepository.save(usuario);

        return ResponseEntity.ok(Map.of("success", true, "theme", theme));
    }
}
