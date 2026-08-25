package com.br.gerenciadoraulas.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamento_matricula")
public class PagamentoMatricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "valor_mensalidade_dia")
    private BigDecimal valorMensalidadeDia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "matricula_id")
    private Matricula matricula;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pagamento_id")
    private Pagamento pagamento;

    public PagamentoMatricula() {
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

    public Matricula getMatricula() {
        return matricula;
    }

    public void setMatricula(Matricula matricula) {
        this.matricula = matricula;
    }

    public Pagamento getPagamento() {
        return pagamento;
    }

    public void setPagamento(Pagamento pagamento) {
        this.pagamento = pagamento;
    }
}
