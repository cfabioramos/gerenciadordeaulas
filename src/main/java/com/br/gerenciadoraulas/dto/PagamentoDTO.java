package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Pagamento;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PagamentoDTO {
    private Long id;
    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    private List<PagamentoMatriculaDTO> itens = new ArrayList<>();

    public PagamentoDTO() {
    }

    public PagamentoDTO(Pagamento pagamento) {
        if (pagamento == null) return;
        this.id = pagamento.getId();
        this.valor = pagamento.getValor();
        this.data = pagamento.getData();
        if (pagamento.getPagamentoMatriculas() != null) {
            this.itens = pagamento.getPagamentoMatriculas().stream()
                    .map(PagamentoMatriculaDTO::new)
                    .collect(Collectors.toList());
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<PagamentoMatriculaDTO> getItens() {
        return itens;
    }

    public void setItens(List<PagamentoMatriculaDTO> itens) {
        this.itens = itens;
    }
}
