package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aula;

import java.time.LocalDate;

public class AulaDTO {
    private Long id;
    private String nome;
    private LocalDate data;
    private Long programaAulaId;
    private String programaAulaNome;

    public AulaDTO(Aula aula) {
        if (aula == null) return;
        this.id = aula.getId();
        this.nome = aula.getNome();
        this.data = aula.getData();
        if (aula.getProgramaAula() != null) {
            this.programaAulaId = aula.getProgramaAula().getId();
            this.programaAulaNome = aula.getProgramaAula().getNome();
        }
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getData() { return data; }
    public Long getProgramaAulaId() { return programaAulaId; }
    public String getProgramaAulaNome() { return programaAulaNome; }
}
