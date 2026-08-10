package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.ProgramaAula;

import java.time.LocalDate;

public class MatriculaDTO {
    private Long id;
    private LocalDate data;
    private Long alunoId;
    private String alunoNome;
    private Long programaAulaId;
    private String programaAulaNome;

    public MatriculaDTO(){}

    public MatriculaDTO(Matricula matricula) {
        if (matricula == null) return;
        this.id = matricula.getId();
        this.data = matricula.getData();
        if (matricula.getAluno() != null) {
            this.alunoId = matricula.getAluno().getId();
            this.alunoNome = matricula.getAluno().getNome();
        }
        if (matricula.getProgramaAula() != null) {
            this.programaAulaId = matricula.getProgramaAula().getId();
            this.programaAulaNome = matricula.getProgramaAula().getNome();
        }
    }

    public Matricula generateMatricula() {
        Matricula matricula = new Matricula();
        matricula.setAluno(new Aluno(this.alunoId));
        matricula.setProgramaAula(new ProgramaAula(this.programaAulaId));
        matricula.setData(LocalDate.now());
        return matricula;
    }

    public Long getId() {
        return id;
    }

    public LocalDate getData() {
        return data;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public String getAlunoNome() {
        return alunoNome;
    }

    public Long getProgramaAulaId() {
        return programaAulaId;
    }

    public String getProgramaAulaNome() {
        return programaAulaNome;
    }
}
