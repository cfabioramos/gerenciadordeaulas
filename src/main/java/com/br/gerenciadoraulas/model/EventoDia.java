package com.br.gerenciadoraulas.model;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "evento_dia")
public class EventoDia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "evento_id", nullable = false)
    private Evento evento;

    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;

    @Column(name = "horario", length = 5, nullable = false)
    private String horario;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Evento getEvento() { return evento; }
    public void setEvento(Evento evento) { this.evento = evento; }
    public LocalDate getDataEvento() { return dataEvento; }
    public void setDataEvento(LocalDate dataEvento) { this.dataEvento = dataEvento; }
    public String getHorario() { return horario; }
    public void setHorario(String horario) { this.horario = horario; }
}