package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.CicloDTO;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.service.CicloService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ciclos")
public class CicloController {

    private final CicloService cicloService;

    public CicloController(CicloService cicloService) {
        this.cicloService = cicloService;
    }

    @GetMapping
    public List<CicloDTO> listarTodas() {
        return cicloService.listarTodas();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CicloDTO> buscarPorId(@PathVariable Long id) {
        return cicloService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<CicloDTO> criar(@RequestBody Ciclo ciclo) {
        CicloDTO dto = cicloService.salvar(ciclo);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CicloDTO> atualizar(@PathVariable Long id, @RequestBody Ciclo ciclo) {
        return cicloService.atualizar(id, ciclo)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        cicloService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
