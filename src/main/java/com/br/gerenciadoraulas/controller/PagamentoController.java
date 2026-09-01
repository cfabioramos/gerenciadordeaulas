package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.CadastroPagamentoDTO;
import com.br.gerenciadoraulas.dto.EditarPagamentoDTO;
import com.br.gerenciadoraulas.dto.PagamentoDTO;
import com.br.gerenciadoraulas.service.PagamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/pagamentos")
public class PagamentoController {

    @Autowired
    private PagamentoService pagamentoService;

    @GetMapping
    public List<PagamentoDTO> listarTodos(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate inicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fim,
            @RequestParam(required = false) Long alunoId,
            @RequestParam(required = false) Long cicloId) {
        return pagamentoService.listarTodos(inicio, fim, alunoId, cicloId);
    }

    @PostMapping
    public ResponseEntity<PagamentoDTO> criar(@RequestBody CadastroPagamentoDTO dto) {
        PagamentoDTO saved = pagamentoService.salvar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping("/aluno/{alunoId}")
    public List<PagamentoDTO> listarPorAluno(@PathVariable Long alunoId) {
        return pagamentoService.listarPorAluno(alunoId);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PagamentoDTO> atualizar(@PathVariable Long id, @RequestBody EditarPagamentoDTO dto) {
        return pagamentoService.atualizar(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        pagamentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
