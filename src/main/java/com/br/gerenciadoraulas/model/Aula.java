package com.br.gerenciadoraulas.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Aula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate data;

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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public ProgramaAula getProgramaAula() {
        return programaAula;
    }

    public void setProgramaAula(ProgramaAula programaAula) {
        this.programaAula = programaAula;
    }
}
