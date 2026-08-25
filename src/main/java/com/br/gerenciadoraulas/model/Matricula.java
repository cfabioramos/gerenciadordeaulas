package com.br.gerenciadoraulas.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Matricula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate data;
    @Column(name = "fl_ativo")
    private Boolean flAtivo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "aluno_id")
    private Aluno aluno;

    @ManyToOne
    @JoinColumn(name = "programaaula_id")
    private ProgramaAula programaAula;

    private Double valor;

    @Column(name = "valor_mensalidade")
    private Double valorMensalidade;

    @Column(name = "dia_vencimento")
    private Integer diaVencimento;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    public ProgramaAula getProgramaAula() {
        return programaAula;
    }

    public void setProgramaAula(ProgramaAula programaAula) {
        this.programaAula = programaAula;
    }

    public Boolean getFlAtivo() {
        return flAtivo;
    }

    public void setFlAtivo(Boolean flAtivo) {
        this.flAtivo = flAtivo;
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
