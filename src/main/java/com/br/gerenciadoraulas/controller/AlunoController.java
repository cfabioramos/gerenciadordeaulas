package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.AlunoDTO;
import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.service.AlunoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
public class AlunoController {

    private final AlunoService alunoService;

    public AlunoController(AlunoService alunoService) {
        this.alunoService = alunoService;
    }

    // Listar todos os alunos (DTOs)
    @GetMapping
    public List<AlunoDTO> listarTodos() {
        return alunoService.listarTodos();
    }

    // Listar alunos não matriculados em um ciclo
    @GetMapping("/nao-matriculados/{cicloId}")
    public List<AlunoDTO> listarNaoMatriculados(@PathVariable Long cicloId) {
        return alunoService.listarNaoMatriculados(cicloId);
    }

    @PostMapping
    public ResponseEntity<AlunoDTO> criar(@RequestBody Aluno aluno) {
        AlunoDTO dto = alunoService.salvar(aluno);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }


}
