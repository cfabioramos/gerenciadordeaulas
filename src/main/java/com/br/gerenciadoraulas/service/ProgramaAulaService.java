package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.ProgramaAulaDTO;
import com.br.gerenciadoraulas.model.ProgramaAula;
import com.br.gerenciadoraulas.repository.ProgramaAulaRepository;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProgramaAulaService {

    private final ProgramaAulaRepository programaAulaRepository;

    public ProgramaAulaService(ProgramaAulaRepository programaAulaRepository) {
        this.programaAulaRepository = programaAulaRepository;
    }

    public List<ProgramaAulaDTO> listarTodas() {
        return programaAulaRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"))
                .stream()
                .map(ProgramaAulaDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<ProgramaAulaDTO> buscarPorId(Long id) {
        return programaAulaRepository.findById(id).map(ProgramaAulaDTO::new);
    }

    public ProgramaAulaDTO salvar(ProgramaAula programaAula) {
        ProgramaAula saved = programaAulaRepository.save(programaAula);
        return new ProgramaAulaDTO(saved);
    }

    public Optional<ProgramaAulaDTO> atualizar(Long id, ProgramaAula programaAula) {
        return programaAulaRepository.findById(id)
                .map(existing -> {
                    existing.setNome(programaAula.getNome());
                    existing.setCiclo(programaAula.getCiclo());
                    existing.setTipoAula(programaAula.getTipoAula());
                    ProgramaAula updated = programaAulaRepository.save(existing);
                    return new ProgramaAulaDTO(updated);
                });
    }

    public void deletar(Long id) {
        programaAulaRepository.deleteById(id);
    }

    public List<ProgramaAulaDTO> listarPorCiclo(Long cicloId) {
        return programaAulaRepository.findByCicloId(cicloId)
                .stream()
                .map(ProgramaAulaDTO::new)
                .collect(Collectors.toList());
    }

}