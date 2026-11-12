package com.br.gerenciadoraulas.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "receita")
public class Receita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String descricao;

    @Column(name = "valor", precision = 10, scale = 2, nullable = false)
    private BigDecimal valor;

    // Evita recursão infinita no JSON (evento -> receitas -> evento -> ...)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id")
    private Evento evento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "participante_id")
    private Participante participante;

    @Column(name = "data_lancamento")
    private LocalDateTime dataLancamento;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public Participante getParticipante() { return participante; }
    public void setParticipante(Participante participante) { this.participante = participante; }
    public LocalDateTime getDataLancamento() { return dataLancamento; }
    public void setDataLancamento(LocalDateTime dataLancamento) { this.dataLancamento = dataLancamento; }
}