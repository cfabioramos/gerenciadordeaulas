package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.AulaDTO;
import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.service.AulaService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/aulas")
public class AulaController {

    private final AulaService aulaService;

    public AulaController(AulaService aulaService) {
        this.aulaService = aulaService;
    }

    // Listar todas as aulas (retorna DTOs)
    @GetMapping
    public List<AulaDTO> listarTodas() {
        return aulaService.listarTodas();
    }

    // Buscar aula por ID
    @GetMapping("/{id}")
    public ResponseEntity<AulaDTO> buscarPorId(@PathVariable Long id) {
        return aulaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Criar nova aula
    @PostMapping
    public AulaDTO criar(@RequestBody Aula aula) {
        return aulaService.salvar(aula);
    }

    // Deletar aula por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        aulaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    // Atualizar aula por ID
    @PutMapping("/{id}")
    public ResponseEntity<AulaDTO> atualizar(@PathVariable Long id, @RequestBody Aula aula) {
        return aulaService.atualizar(id, aula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar aulas por data/hora (usando findByData)
    @GetMapping("/data/{data}")
    public List<AulaDTO> buscarPorData(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime data) {
        return aulaService.findByData(data);
    }

    // Buscar aulas por programa de aula
    @GetMapping("/programaAula/{programaAulaId}")
    public List<AulaDTO> listarPorProgramaAula(@PathVariable Long programaAulaId) {
        return aulaService.listarPorProgramaAula(programaAulaId);
    }
}
