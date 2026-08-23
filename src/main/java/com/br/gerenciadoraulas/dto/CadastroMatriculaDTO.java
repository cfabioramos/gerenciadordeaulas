package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.ProgramaAula;

import java.time.LocalDate;

public class CadastroMatriculaDTO {
    private Long alunoId;
    private Long programaAulaId;

    public CadastroMatriculaDTO() {
    }

    public CadastroMatriculaDTO(Long alunoId, Long programaAulaId) {
        this.alunoId = alunoId;
        this.programaAulaId = programaAulaId;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public Long getProgramaAulaId() {
        return programaAulaId;
    }

    public void setProgramaAulaId(Long programaAulaId) {
        this.programaAulaId = programaAulaId;
    }

    public Matricula generateMatricula() {
        Matricula matricula = new Matricula();
        matricula.setAluno(new Aluno(this.alunoId));
        matricula.setProgramaAula(new ProgramaAula(this.programaAulaId));
        matricula.setData(LocalDate.now());
        matricula.setFlAtivo(true);
        return matricula;
    }
}
