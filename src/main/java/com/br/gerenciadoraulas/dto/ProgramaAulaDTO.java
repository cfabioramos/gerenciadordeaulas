package com.br.gerenciadoraulas.dto;

import com.br.gerenciadoraulas.model.ProgramaAula;

public class ProgramaAulaDTO {
    private Long id;
    private String nome;
    private Long cicloId;
    private String cicloNome;
    private Long tipoAulaId;
    private String tipoAulaNome;

    public ProgramaAulaDTO(ProgramaAula p) {
        if (p == null) return;
        this.id = p.getId();
        this.nome = p.getNome();
        if (p.getCiclo() != null) {
            this.cicloId = p.getCiclo().getId();
            this.cicloNome = p.getCiclo().getNome();
        }
        if (p.getTipoAula() != null) {
            this.tipoAulaId = p.getTipoAula().getId();
            this.tipoAulaNome = p.getTipoAula().getNome();
        }
    }

    public Long getId() { return id; }
    public String getNome() { return nome; }
    public Long getCicloId() { return cicloId; }
    public String getCicloNome() { return cicloNome; }
    public Long getTipoAulaId() { return tipoAulaId; }
    public String getTipoAulaNome() { return tipoAulaNome; }
}
