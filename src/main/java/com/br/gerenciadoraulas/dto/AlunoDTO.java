package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;

public class AlunoDTO {
    private Long id;
    private String nome;

    public AlunoDTO(Aluno aluno) {
        if (aluno == null) return;
        this.id = aluno.getId();
        this.nome = aluno.getNome();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }
}
