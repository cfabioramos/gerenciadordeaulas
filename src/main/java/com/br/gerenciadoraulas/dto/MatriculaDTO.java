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
    private Boolean presente;
    private Long presencaId;

    public MatriculaDTO(){}

    public MatriculaDTO(Long id, LocalDate data, Long alunoId, String alunoNome, Long programaAulaId, String programaAulaNome, Boolean presente) {
        this(id, data, alunoId, alunoNome, programaAulaId, programaAulaNome, presente, null);
    }

    public MatriculaDTO(Long id, LocalDate data, Long alunoId, String alunoNome, Long programaAulaId, String programaAulaNome, Boolean presente, Long presencaId) {
        this.id = id;
        this.data = data;
        this.alunoId = alunoId;
        this.alunoNome = alunoNome;
        this.programaAulaId = programaAulaId;
        this.programaAulaNome = programaAulaNome;
        this.presente = presente;
        this.presencaId = presencaId;
    }

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

    public Boolean getPresente() {
        return presente;
    }

    public void setPresente(Boolean presente) {
        this.presente = presente;
    }

    public Long getPresencaId() {
        return presencaId;
    }

    public void setPresencaId(Long presencaId) {
        this.presencaId = presencaId;
    }
}
