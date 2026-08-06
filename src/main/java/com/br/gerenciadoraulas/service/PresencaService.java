package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.PresencaDTO;
import com.br.gerenciadoraulas.model.Presenca;
import com.br.gerenciadoraulas.repository.PresencaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PresencaService {

    private final PresencaRepository presencaRepository;

    public PresencaService(PresencaRepository presencaRepository) {
        this.presencaRepository = presencaRepository;
    }

    public List<PresencaDTO> listarTodas() {
        return presencaRepository.findAll()
                .stream()
                .map(PresencaDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<PresencaDTO> buscarPorId(Long id) {
        return presencaRepository.findById(id).map(PresencaDTO::new);
    }

    public PresencaDTO salvar(Presenca presenca) {
        Presenca saved = presencaRepository.save(presenca);
        return new PresencaDTO(saved);
    }

    public void deletar(Long id) {
        presencaRepository.deleteById(id);
    }

    @Transactional
    public void deletarPorMatriculaEAula(Long matriculaId, Long aulaId) {
        presencaRepository.deleteByMatriculaIdAndAulaId(matriculaId, aulaId);
    }
}