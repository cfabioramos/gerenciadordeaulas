package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Presenca;

public class PresencaDTO {
    private Long id;
    private Long aulaId;
    private Long matriculaId;

    public PresencaDTO(Presenca presenca) {
        if (presenca == null) return;
        this.id = presenca.getId();
        if (presenca.getAula() != null) this.aulaId = presenca.getAula().getId();
        if (presenca.getMatricula() != null) this.matriculaId = presenca.getMatricula().getId();
    }

    public Long getId() { return id; }
    public Long getAulaId() { return aulaId; }
    public Long getMatriculaId() { return matriculaId; }
}
