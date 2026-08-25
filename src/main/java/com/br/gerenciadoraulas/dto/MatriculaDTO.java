package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.ProgramaAula;

import java.math.BigDecimal;
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
    private Boolean flAtivo;
    private BigDecimal valor;
    private BigDecimal valorMensalidade;
    private Integer diaVencimento;

    public MatriculaDTO(){}

    public MatriculaDTO(Long id, LocalDate data, Boolean flAtivo, Long alunoId, String alunoNome, Long programaAulaId, String programaAulaNome, Boolean presente) {
        this(id, data, flAtivo, alunoId, alunoNome, programaAulaId, programaAulaNome, presente, null);
    }

    public MatriculaDTO(Long id, LocalDate data, Boolean flAtivo, Long alunoId, String alunoNome, Long programaAulaId, String programaAulaNome, Boolean presente, Long presencaId) {
        this.id = id;
        this.data = data;
        this.alunoId = alunoId;
        this.alunoNome = alunoNome;
        this.flAtivo = flAtivo;
        this.programaAulaId = programaAulaId;
        this.programaAulaNome = programaAulaNome;
        this.presente = presente;
        this.presencaId = presencaId;
    }

    public MatriculaDTO(Matricula matricula) {
        if (matricula == null) return;
        this.id = matricula.getId();
        this.data = matricula.getData();
        this.flAtivo = matricula.getFlAtivo();
        if (matricula.getAluno() != null) {
            this.alunoId = matricula.getAluno().getId();
            this.alunoNome = matricula.getAluno().getNome();
        }
        if (matricula.getProgramaAula() != null) {
            this.programaAulaId = matricula.getProgramaAula().getId();
            this.programaAulaNome = matricula.getProgramaAula().getNome();
        }
        this.valor = matricula.getValor();
        this.valorMensalidade = matricula.getValorMensalidade();
        this.diaVencimento = matricula.getDiaVencimento();
    }

    public Matricula generateMatricula() {
        Matricula matricula = new Matricula();
        matricula.setAluno(new Aluno(this.alunoId));
        matricula.setProgramaAula(new ProgramaAula(this.programaAulaId));
        matricula.setData(LocalDate.now());
        matricula.setFlAtivo(this.flAtivo);
        matricula.setValor(this.valor);
        matricula.setValorMensalidade(this.valorMensalidade);
        matricula.setDiaVencimento(this.diaVencimento);
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

    public Boolean getFlAtivo() {
        return flAtivo;
    }

    public void setFlAtivo(Boolean flAtivo) {
        this.flAtivo = flAtivo;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public BigDecimal getValorMensalidade() {
        return valorMensalidade;
    }

    public void setValorMensalidade(BigDecimal valorMensalidade) {
        this.valorMensalidade = valorMensalidade;
    }

    public Integer getDiaVencimento() {
        return diaVencimento;
    }

    public void setDiaVencimento(Integer diaVencimento) {
        this.diaVencimento = diaVencimento;
    }
}
