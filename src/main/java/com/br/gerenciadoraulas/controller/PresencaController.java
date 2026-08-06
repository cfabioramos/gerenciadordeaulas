package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.PresencaDTO;
import com.br.gerenciadoraulas.model.Presenca;
import com.br.gerenciadoraulas.service.PresencaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/presencas")
public class PresencaController {

    private final PresencaService presencaService;

    public PresencaController(PresencaService presencaService) {
        this.presencaService = presencaService;
    }

    @GetMapping
    public List<PresencaDTO> listarTodas() {
        return presencaService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<PresencaDTO> buscarPorId(@PathVariable Long id) {
        return presencaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<PresencaDTO> criar(@RequestBody Presenca presenca) {
        PresencaDTO dto = presencaService.salvar(presenca);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        presencaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Deletar presenças por matrícula e aula
    @DeleteMapping("/matricula/{matriculaId}/aula/{aulaId}")
    public ResponseEntity<Void> deletarPorMatriculaEAula(@PathVariable Long matriculaId, @PathVariable Long aulaId) {
        presencaService.deletarPorMatriculaEAula(matriculaId, aulaId);
        return ResponseEntity.noContent().build();
    }
}