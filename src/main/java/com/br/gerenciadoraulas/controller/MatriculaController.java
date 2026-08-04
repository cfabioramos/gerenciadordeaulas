package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.MatriculaDTO;
import com.br.gerenciadoraulas.service.MatriculaService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // Buscar alunos matriculados em um programa de aula específico
    @GetMapping("/programaAula/{programaAulaId}")
    public List<MatriculaDTO> listarPorProgramaAula(@PathVariable Long programaAulaId) {
        return matriculaService.listarPorProgramaAula(programaAulaId)
                .stream()
                .map(MatriculaDTO::new)
                .collect(Collectors.toList());
    }
}
