package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.TipoAulaDTO;
import com.br.gerenciadoraulas.repository.TipoAulaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tipo-aulas")
public class TipoAulaController {

    private final TipoAulaRepository tipoAulaRepository;

    public TipoAulaController(TipoAulaRepository tipoAulaRepository) {
        this.tipoAulaRepository = tipoAulaRepository;
    }

    @GetMapping
    public List<TipoAulaDTO> listarTodos() {
        return tipoAulaRepository.findAll()
                .stream()
                .map(TipoAulaDTO::new)
                .collect(Collectors.toList());
    }
}
