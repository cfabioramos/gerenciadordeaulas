package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.model.Despesa;
import com.br.gerenciadoraulas.service.DespesaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/despesas")
public class DespesaController {

    @Autowired
    private DespesaService despesaService;

    @GetMapping("/evento/{eventoId}")
    public List<Despesa> listarPorEvento(@PathVariable Long eventoId) {
        return despesaService.listarPorEvento(eventoId);
    }

    @PostMapping
    public Despesa salvar(@RequestBody Despesa despesa) {
        return despesaService.salvar(despesa);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        despesaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}