package com.br.gerenciadoraulas.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CadastroPagamentoDTO {
    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    private List<Long> matriculaIds;

    public CadastroPagamentoDTO() {
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public List<Long> getMatriculaIds() {
        return matriculaIds;
    }

    public void setMatriculaIds(List<Long> matriculaIds) {
        this.matriculaIds = matriculaIds;
    }
}
