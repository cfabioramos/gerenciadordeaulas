package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.model.ProgramaAula;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CicloDTO {
    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private List<Long> programaAulaIds = Collections.emptyList();

    public CicloDTO(Ciclo ciclo) {
        if (ciclo == null) return;
        this.id = ciclo.getId();
        this.nome = ciclo.getNome();
        this.dataInicio = ciclo.getDataInicio();
        this.dataFim = ciclo.getDataFim();
        if (ciclo.getProgramaAulas() != null) {
            this.programaAulaIds = ciclo.getProgramaAulas()
                    .stream()
                    .map(ProgramaAula::getId)
                    .collect(Collectors.toList());
        }
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public LocalDate getDataInicio() { return dataInicio; }
    public LocalDate getDataFim() { return dataFim; }
    public List<Long> getProgramaAulaIds() { return programaAulaIds; }
}
