package com.br.gerenciadoraulas.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "evento")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "text")
    private String descricao;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 20)
    private TipoEvento tipo;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;

    @Column(name = "horario_inicio", length = 5)
    private String horarioInicio;

    @Column(name = "horario_fim", length = 5)
    private String horarioFim;

    @Column(name = "local", length = 100)
    private String local;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @Column(name = "participantes_qtd")
    private Integer participantesQtd;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<EventoDia> dias;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Participante> participantes;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Receita> receitas;

    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Despesa> despesas;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
    public TipoEvento getTipo() { return tipo; }
    public void setTipo(TipoEvento tipo) { this.tipo = tipo; }
    public LocalDateTime getDataInicio() { return dataInicio; }
    public void setDataInicio(LocalDateTime dataInicio) { this.dataInicio = dataInicio; }
    public LocalDateTime getDataFim() { return dataFim; }
    public void setDataFim(LocalDateTime dataFim) { this.dataFim = dataFim; }
    public String getHorarioInicio() { return horarioInicio; }
    public void setHorarioInicio(String horarioInicio) { this.horarioInicio = horarioInicio; }
    public String getHorarioFim() { return horarioFim; }
    public void setHorarioFim(String horarioFim) { this.horarioFim = horarioFim; }
    public String getLocal() { return local; }
    public void setLocal(String local) { this.local = local; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public Integer getParticipantesQtd() { return participantesQtd; }
    public void setParticipantesQtd(Integer participantesQtd) { this.participantesQtd = participantesQtd; }
    public Set<EventoDia> getDias() { return dias; }
    public void setDias(Set<EventoDia> dias) { this.dias = dias; }
    public Set<Participante> getParticipantes() { return participantes; }
    public void setParticipantes(Set<Participante> participantes) { this.participantes = participantes; }
    public Set<Receita> getReceitas() { return receitas; }
    public void setReceitas(Set<Receita> receitas) { this.receitas = receitas; }
    public Set<Despesa> getDespesas() { return despesas; }
    public void setDespesas(Set<Despesa> despesas) { this.despesas = despesas; }
}