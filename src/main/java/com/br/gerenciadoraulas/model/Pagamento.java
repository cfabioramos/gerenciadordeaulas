package com.br.gerenciadoraulas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal valor;
    private LocalDate data;

    @OneToMany(mappedBy = "pagamento", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PagamentoMatricula> pagamentoMatriculas = new ArrayList<>();

    public Pagamento() {
    }

    public Pagamento(Long id) {
        this.id = id;
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

    public List<PagamentoMatricula> getPagamentoMatriculas() {
        return pagamentoMatriculas;
    }

    public void setPagamentoMatriculas(List<PagamentoMatricula> pagamentoMatriculas) {
        this.pagamentoMatriculas = pagamentoMatriculas;
    }
}
