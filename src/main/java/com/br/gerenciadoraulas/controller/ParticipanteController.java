package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.model.Participante;
import com.br.gerenciadoraulas.service.ParticipanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/participantes")
public class ParticipanteController {

    @Autowired
    private ParticipanteService participanteService;

    @GetMapping("/evento/{eventoId}")
    public List<Participante> listarPorEvento(@PathVariable Long eventoId) {
        return participanteService.listarPorEvento(eventoId);
    }

    @PostMapping
    public Participante salvar(@RequestBody Participante participante) {
        return participanteService.salvar(participante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        participanteService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}