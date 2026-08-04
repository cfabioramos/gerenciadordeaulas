package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.TipoAula;

public class TipoAulaDTO {
    private Long id;
    private String nome;

    public TipoAulaDTO(TipoAula tipoAula) {
        if (tipoAula == null) return;
        this.id = tipoAula.getId();
        this.nome = tipoAula.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
