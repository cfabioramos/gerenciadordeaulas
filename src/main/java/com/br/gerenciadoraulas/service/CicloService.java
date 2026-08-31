package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.CicloDTO;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.repository.CicloRepository;
import com.br.gerenciadoraulas.repository.ProgramaAulaRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CicloService {

    private final CicloRepository cicloRepository;
    private final ProgramaAulaRepository programaAulaRepository;

    public CicloService(CicloRepository cicloRepository, ProgramaAulaRepository programaAulaRepository) {
        this.cicloRepository = cicloRepository;
        this.programaAulaRepository = programaAulaRepository;
    }

    public List<CicloDTO> listarTodas() {
            return cicloRepository.findAll(Sort.by(Sort.Direction.ASC, "dataInicio"))
                .stream()
                .map(CicloDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<CicloDTO> buscarPorId(Long id) {
        return cicloRepository.findById(id).map(CicloDTO::new);
    }

    public CicloDTO salvar(Ciclo ciclo) {
        Ciclo saved = cicloRepository.save(ciclo);
        return new CicloDTO(saved);
    }

    public Optional<CicloDTO> atualizar(Long id, Ciclo ciclo) {
        return cicloRepository.findById(id)
                .map(existing -> {
                    existing.setNome(ciclo.getNome());
                    existing.setDataInicio(ciclo.getDataInicio());
                    existing.setDataFim(ciclo.getDataFim());
                    Ciclo updated = cicloRepository.save(existing);
                    return new CicloDTO(updated);
                });
    }

    public void deletar(Long id) {
        if (programaAulaRepository.existsByCicloId(id)) {
            throw new IllegalStateException("Não é possível excluir o ciclo de aulas pois existem programas de aula vinculados a ele.");
        }
        cicloRepository.deleteById(id);
    }
}
