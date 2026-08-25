package com.br.gerenciadoraulas.dto;

import java.math.BigDecimal;

public class AtualizarMatriculaDTO {
    private BigDecimal valor;
    private BigDecimal valorMensalidade;
    private Integer diaVencimento;

    public AtualizarMatriculaDTO() {
    }

    public AtualizarMatriculaDTO(BigDecimal valor, BigDecimal valorMensalidade, Integer diaVencimento) {
        this.valor = valor;
        this.valorMensalidade = valorMensalidade;
        this.diaVencimento = diaVencimento;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(BigDecimal valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }
}
