package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.AtualizarMatriculaDTO;
import com.br.gerenciadoraulas.dto.CadastroMatriculaDTO;
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
    public ResponseEntity<MatriculaDTO> criar(@RequestBody CadastroMatriculaDTO dto) {
        MatriculaDTO saved = this.matriculaService.salvar(dto.generateMatricula());
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        this.matriculaService.deletar(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<MatriculaDTO> atualizar(@PathVariable Long id, @RequestBody AtualizarMatriculaDTO dto) {
        return this.matriculaService.buscarPorId(id).map(matricula -> {
            matricula.setValor(dto.getValor());
            matricula.setValorMensalidade(dto.getValorMensalidade());
            matricula.setDiaVencimento(dto.getDiaVencimento());
            if (dto.getFlAtivo() != null) {
                matricula.setFlAtivo(dto.getFlAtivo());
            }
            MatriculaDTO updated = this.matriculaService.salvar(matricula);
            return ResponseEntity.ok(updated);
        }).orElse(ResponseEntity.notFound().build());
    }

    @RequestMapping(value = "/{id}/status", method = {RequestMethod.PATCH, RequestMethod.PUT})
    public ResponseEntity<Void> atualizarStatus(
            @PathVariable Long id,
            @RequestParam(required = false) Boolean ativo,
            @RequestBody(required = false) java.util.Map<String, Object> body) {
        Boolean novoStatus = ativo;
        if (novoStatus == null && body != null) {
            Object val = body.get("ativo");
            if (val == null) val = body.get("flAtivo");
            if (val instanceof Boolean) novoStatus = (Boolean) val;
            else if (val != null) novoStatus = Boolean.parseBoolean(val.toString());
        }
        this.matriculaService.atualizarStatus(id, novoStatus != null ? novoStatus : true);
        return ResponseEntity.ok().build();
    }

    // Listar matrículas de uma aula com flag de presença
    @GetMapping("/aula/{aulaId}")
    public List<MatriculaDTO> consultarMatriculasPorAula(@PathVariable Long aulaId) {
        return matriculaService.consultarMatriculasPorAula(aulaId);
    }

}
