package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.PagamentoMatricula;
import java.math.BigDecimal;

public class PagamentoMatriculaDTO {
    private Long id;
    private BigDecimal valorMensalidadeDia;
    private Long matriculaId;
    private String programaAulaNome;

    public PagamentoMatriculaDTO() {
    }

    public PagamentoMatriculaDTO(PagamentoMatricula pm) {
        if (pm == null) return;
        this.id = pm.getId();
        this.valorMensalidadeDia = pm.getValorMensalidadeDia();
        if (pm.getMatricula() != null) {
            this.matriculaId = pm.getMatricula().getId();
            if (pm.getMatricula().getProgramaAula() != null) {
                this.programaAulaNome = pm.getMatricula().getProgramaAula().getNome();
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorMensalidadeDia() {
        return valorMensalidadeDia;
    }

    public void setValorMensalidadeDia(BigDecimal valorMensalidadeDia) {
        this.valorMensalidadeDia = valorMensalidadeDia;
    }

    public Long getMatriculaId() {
        return matriculaId;
    }

    public void setMatriculaId(Long matriculaId) {
        this.matriculaId = matriculaId;
    }

    public String getProgramaAulaNome() {
        return programaAulaNome;
    }

    public void setProgramaAulaNome(String programaAulaNome) {
        this.programaAulaNome = programaAulaNome;
    }
}
