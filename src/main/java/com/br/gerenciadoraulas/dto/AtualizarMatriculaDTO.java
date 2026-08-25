package com.br.gerenciadoraulas.dto;

public class AtualizarMatriculaDTO {
    private Double valor;
    private Double valorMensalidade;
    private Integer diaVencimento;

    public AtualizarMatriculaDTO() {
    }

    public AtualizarMatriculaDTO(Double valor, Double valorMensalidade, Integer diaVencimento) {
        this.valor = valor;
        this.valorMensalidade = valorMensalidade;
        this.diaVencimento = diaVencimento;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(Double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }
}
