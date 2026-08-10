package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.MatriculaDTO;
import com.br.gerenciadoraulas.service.MatriculaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/matriculas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    public MatriculaController(MatriculaService matriculaService) {
        this.matriculaService = matriculaService;
    }

    // Listar todas as matrículas (retorna DTOs para resposta JSON)
    @GetMapping
    public List<MatriculaDTO> listarTodas() {
        return matriculaService.listarTodas()
                .stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }

    // Listar as matriculas de um determinado aluno
    @GetMapping("/aluno/{alunoId}")
    public List<MatriculaDTO> listarPorAluno(@PathVariable Long alunoId) {
        return this.matriculaService.listarPorAluno(alunoId);
    }

    // Buscar alunos matriculados em um programa de aula específico
    @GetMapping("/programaAula/{programaAulaId}")
    public List<MatriculaDTO> listarPorProgramaAula(@PathVariable Long programaAulaId) {
        return matriculaService.listarPorProgramaAula(programaAulaId)
                .stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity<MatriculaDTO> criar(@RequestBody MatriculaDTO dto) {
        MatriculaDTO saved = this.matriculaService.salvar(dto.generateMatricula());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

}
