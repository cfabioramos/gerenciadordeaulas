package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.ProgramaAulaDTO;
import com.br.gerenciadoraulas.model.ProgramaAula;
import com.br.gerenciadoraulas.service.ProgramaAulaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/programa-aulas")
public class ProgramaAulaController {

    private final ProgramaAulaService programaAulaService;

    public ProgramaAulaController(ProgramaAulaService programaAulaService) {
        this.programaAulaService = programaAulaService;
    }

    @GetMapping
    public List<ProgramaAulaDTO> listarTodas() {
        return programaAulaService.listarTodas();
    }

    @GetMapping("/ciclo/{cicloId}")
    public List<ProgramaAulaDTO> listarPorCiclo(@PathVariable Long cicloId) {
        return programaAulaService.listarPorCiclo(cicloId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProgramaAulaDTO> buscarPorId(@PathVariable Long id) {
        return programaAulaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ProgramaAulaDTO> criar(@RequestBody ProgramaAula programaAula) {
        ProgramaAulaDTO dto = programaAulaService.salvar(programaAula);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProgramaAulaDTO> atualizar(@PathVariable Long id, @RequestBody ProgramaAula programaAula) {
        return programaAulaService.atualizar(id, programaAula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        programaAulaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}