package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.ProgramaAula;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDate;

public class CadastroMatriculaDTO {
    private Long alunoId;
    private Long programaAulaId;
    private Double valor;
    private Double valorMensalidade;
    private Integer diaVencimento;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

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
        matricula.setData(this.data != null ? this.data : LocalDate.now());
        matricula.setFlAtivo(true);
        matricula.setValor(this.valor);
        matricula.setValorMensalidade(this.valorMensalidade);
        matricula.setDiaVencimento(this.diaVencimento);
        return matricula;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Double getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(Double valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }
}
