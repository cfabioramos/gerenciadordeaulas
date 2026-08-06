package com.br.gerenciadoraulas.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDateTime data;

    @ManyToOne
    @JoinColumn(name = "programaaula_id")
    private ProgramaAula programaAula;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public ProgramaAula getProgramaAula() {
        return programaAula;
    }

    public void setProgramaAula(ProgramaAula programaAula) {
        this.programaAula = programaAula;
    }
}
