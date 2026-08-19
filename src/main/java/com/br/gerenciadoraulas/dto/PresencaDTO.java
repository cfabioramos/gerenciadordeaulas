package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Presenca;

public class PresencaDTO {
    private Long id;
    private Long matriculaId;
    private Long aulaId;

    public PresencaDTO() {
    }

    public PresencaDTO(Long matriculaId, Long aulaId) {
        this.matriculaId = matriculaId;
        this.aulaId = aulaId;
    }

    public PresencaDTO(Presenca presenca) {
        if (presenca == null) return;
        this.id = presenca.getId();
        if (presenca.getAula() != null) {
            this.aulaId = presenca.getAula().getId();
        }
        if (presenca.getMatricula() != null) {
            this.matriculaId = presenca.getMatricula().getId();
        }
    }

    public Presenca generatePresenca() {
        Presenca presenca = new Presenca();
        if (this.id != null) {
            presenca.setId(this.id);
        }
        if (this.matriculaId != null) {
            Matricula matricula = new Matricula();
            matricula.setId(this.matriculaId);
            presenca.setMatricula(matricula);
        }
        if (this.aulaId != null) {
            Aula aula = new Aula();
            aula.setId(this.aulaId);
            presenca.setAula(aula);
        }
        return presenca;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public Long getAulaId() {
        return aulaId;
    }

    public void setAulaId(Long aulaId) {
        this.aulaId = aulaId;
    }
}

