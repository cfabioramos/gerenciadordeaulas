package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;

public class AlunoDTO {
    private Long id;
    private String nome;
    private Boolean presente;

    public AlunoDTO(Aluno aluno) {
        if (aluno == null) return;
        this.id = aluno.getId();
        this.nome = aluno.getNome();
    }

    public AlunoDTO(Long id, String nome, Boolean presente) {
        this.id = id;
        this.nome = nome;
        this.presente = presente;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }
}
