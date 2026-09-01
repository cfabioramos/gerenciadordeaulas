package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Pagamento;
import com.br.gerenciadoraulas.model.PagamentoMatricula;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PagamentoDTO {
    private Long id;
    private BigDecimal valor;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate data;

    private Long alunoId;
    private String alunoNome;
    private Long cicloId;
    private String cicloNome;

    private List<PagamentoMatriculaDTO> itens = new ArrayList<>();

    public PagamentoDTO() {
    }

    public PagamentoDTO(Pagamento pagamento) {
        if (pagamento == null) return;
        this.id = pagamento.getId();
        this.valor = pagamento.getValor();
        this.data = pagamento.getData();
        if (pagamento.getPagamentoMatriculas() != null) {
            this.itens = pagamento.getPagamentoMatriculas().stream()
                    .map(PagamentoMatriculaDTO::new)
                    .collect(Collectors.toList());

            if (!pagamento.getPagamentoMatriculas().isEmpty()) {
                PagamentoMatricula firstPm = pagamento.getPagamentoMatriculas().get(0);
                Matricula matricula = firstPm.getMatricula();
                if (matricula != null) {
                    if (matricula.getAluno() != null) {
                        this.alunoId = matricula.getAluno().getId();
                        this.alunoNome = matricula.getAluno().getNome();
                    }
                    if (matricula.getProgramaAula() != null && matricula.getProgramaAula().getCiclo() != null) {
                        this.cicloId = matricula.getProgramaAula().getCiclo().getId();
                        this.cicloNome = matricula.getProgramaAula().getCiclo().getNome();
                    }
                }
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Long getAlunoId() {
        return alunoId;
    }

    public void setAlunoId(Long alunoId) {
        this.alunoId = alunoId;
    }

    public String getAlunoNome() {
        return alunoNome;
    }

    public void setAlunoNome(String alunoNome) {
        this.alunoNome = alunoNome;
    }

    public Long getCicloId() {
        return cicloId;
    }

    public void setCicloId(Long cicloId) {
        this.cicloId = cicloId;
    }

    public String getCicloNome() {
        return cicloNome;
    }

    public void setCicloNome(String cicloNome) {
        this.cicloNome = cicloNome;
    }

    public List<PagamentoMatriculaDTO> getItens() {
        return itens;
    }

    public void setItens(List<PagamentoMatriculaDTO> itens) {
        this.itens = itens;
    }
}
