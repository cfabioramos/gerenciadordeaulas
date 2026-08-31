package com.br.gerenciadoraulas.config;

import com.br.gerenciadoraulas.model.Usuario;
import com.br.gerenciadoraulas.repository.UsuarioRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final UsuarioRepository usuarioRepository;
    private final JdbcTemplate jdbcTemplate;

    public DatabaseSeeder(UsuarioRepository usuarioRepository, JdbcTemplate jdbcTemplate) {
        this.usuarioRepository = usuarioRepository;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void run(String... args) throws Exception {
        try {
            // Create table if it doesn't exist
            jdbcTemplate.execute("CREATE TABLE IF NOT EXISTS usuario (" +
                    "id SERIAL PRIMARY KEY, " +
                    "username VARCHAR(100) NOT NULL UNIQUE, " +
                    "email VARCHAR(100) NOT NULL UNIQUE, " +
                    "password VARCHAR(255) NOT NULL, " +
                    "admin BOOLEAN NOT NULL, " +
                    "theme VARCHAR(50) DEFAULT 'nordeste' NOT NULL, " +
                    "nome VARCHAR(100)" +
                    ")");

            // Altera tabela para adicionar coluna se já existir de uma execução anterior
            jdbcTemplate.execute("ALTER TABLE usuario ADD COLUMN IF NOT EXISTS theme VARCHAR(50) DEFAULT 'nordeste' NOT NULL");
            jdbcTemplate.execute("ALTER TABLE usuario ADD COLUMN IF NOT EXISTS nome VARCHAR(100)");

            // Atualiza nomes de usuários existentes
            jdbcTemplate.execute("UPDATE usuario SET nome = 'Marcos Affonso' WHERE username = 'marcosaffonso' AND (nome IS NULL OR nome = '')");
            jdbcTemplate.execute("UPDATE usuario SET nome = 'Carlos Fábio' WHERE username = 'cfabioramos' AND (nome IS NULL OR nome = '')");
            jdbcTemplate.execute("UPDATE usuario SET nome = 'Carlos Fábio R' WHERE username = 'cfabioramos.remote' AND (nome IS NULL OR nome = '')");

            try {
                jdbcTemplate.execute("UPDATE matricula SET fl_ativo = true WHERE fl_ativo IS NULL");
            } catch (Exception ex) {
                System.err.println("Aviso migração matricula fl_ativo: " + ex.getMessage());
            }

            // Atualiza chaves estrangeiras para ON DELETE RESTRICT (evitando cascade delete)
            try {
                jdbcTemplate.execute("ALTER TABLE presenca DROP CONSTRAINT IF EXISTS fk_presenca_aula");
                jdbcTemplate.execute("ALTER TABLE presenca ADD CONSTRAINT fk_presenca_aula FOREIGN KEY (aula_id) REFERENCES aula (id) ON DELETE RESTRICT");
            } catch (Exception ex) {
                System.err.println("Aviso migração presenca FK: " + ex.getMessage());
            }

            try {
                jdbcTemplate.execute("ALTER TABLE aula DROP CONSTRAINT IF EXISTS fk_aula_ciclo");
                jdbcTemplate.execute("ALTER TABLE aula DROP CONSTRAINT IF EXISTS fk_aula_programaaula");
                jdbcTemplate.execute("ALTER TABLE aula DROP CONSTRAINT IF EXISTS fk_aula_programa_aula");
                jdbcTemplate.execute("ALTER TABLE aula ADD CONSTRAINT fk_aula_programaaula FOREIGN KEY (programaaula_id) REFERENCES programa_aula (id) ON DELETE RESTRICT");
            } catch (Exception ex) {
                System.err.println("Aviso migração aula FK: " + ex.getMessage());
            }

            try {
                jdbcTemplate.execute("ALTER TABLE programa_aula DROP CONSTRAINT IF EXISTS fk_programaaula_ciclo");
                jdbcTemplate.execute("ALTER TABLE programa_aula DROP CONSTRAINT IF EXISTS fk_programa_aula_ciclo");
                jdbcTemplate.execute("ALTER TABLE programa_aula ADD CONSTRAINT fk_programaaula_ciclo FOREIGN KEY (ciclo_id) REFERENCES ciclo (id) ON DELETE RESTRICT");
            } catch (Exception ex) {
                System.err.println("Aviso migração programa_aula FK: " + ex.getMessage());
            }

            if (usuarioRepository.count() == 0) {
                seedUsers();
            }
        } catch (Exception e) {
            System.err.println("Erro ao inicializar/semear banco de dados: " + e.getMessage());
        }
    }

    private void seedUsers() {
        usuarioRepository.save(new Usuario(
                "marcosaffonso",
                "marcosaffonso@marcosaffonsodanca.com.br",
                BCrypt.hashpw("marcosaffonsodanca123", BCrypt.gensalt()),
                true,
                "Marcos Affonso"
        ));

        usuarioRepository.save(new Usuario(
                "cfabioramos",
                "cfabioramos@gmail.com",
                BCrypt.hashpw("caramilt05", BCrypt.gensalt()),
                true,
                "Carlos Fábio"
        ));

        usuarioRepository.save(new Usuario(
                "cfabioramos.remote",
                "cfabioramos.remote@gmail.com",
                BCrypt.hashpw("caramilt05", BCrypt.gensalt()),
                false,
                "Carlos Fábio R"
        ));

        System.out.println("Usuários iniciais cadastrados com sucesso!");
    }
}
