package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.model.EventoDia;
import com.br.gerenciadoraulas.service.EventoDiaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/evento-dias")
public class EventoDiaController {

    @Autowired
    private EventoDiaService eventoDiaService;

    @GetMapping("/evento/{eventoId}")
    public List<EventoDia> listarPorEvento(@PathVariable Long eventoId) {
        return eventoDiaService.listarPorEvento(eventoId);
    }

    @PostMapping
    public EventoDia salvar(@RequestBody EventoDia eventoDia) {
        return eventoDiaService.salvar(eventoDia);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        eventoDiaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}