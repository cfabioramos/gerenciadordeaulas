package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.model.Receita;
import com.br.gerenciadoraulas.service.ReceitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receitas")
public class ReceitaController {

    @Autowired
    private ReceitaService receitaService;

    @GetMapping("/evento/{eventoId}")
    public List<Receita> listarPorEvento(@PathVariable Long eventoId) {
        return receitaService.listarPorEvento(eventoId);
    }

    @PostMapping
    public Receita salvar(@RequestBody Receita receita) {
        return receitaService.salvar(receita);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        receitaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}