package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.AulaDTO;
import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.repository.AulaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AulaService {

    private final AulaRepository aulaRepository;

    public AulaService(AulaRepository aulaRepository) {
        this.aulaRepository = aulaRepository;
    }

    public List<AulaDTO> listarTodas() {
        return aulaRepository.findAll()
                .stream()
                .map(AulaDTO::new)
                .collect(Collectors.toList());
    }

    public List<AulaDTO> findByData(LocalDateTime data){
        return aulaRepository.findByData(data)
                .stream()
                .map(AulaDTO::new)
                .collect(Collectors.toList());
    }

    public List<AulaDTO> findByDataBetween(LocalDateTime inicio, LocalDateTime fim){
        return aulaRepository.findByDataBetween(inicio, fim)
                .stream()
                .map(AulaDTO::new)
                .collect(Collectors.toList());
    }

    public List<AulaDTO> listarPorProgramaAula(Long programaAulaId) {
        return aulaRepository.findByProgramaAulaId(programaAulaId)
                .stream()
                .map(AulaDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<AulaDTO> buscarPorId(Long id) {
        return aulaRepository.findById(id).map(AulaDTO::new);
    }

    public AulaDTO salvar(Aula aula) {
        Aula saved = aulaRepository.save(aula);
        return new AulaDTO(saved);
    }

    public void deletar(Long id) {
        aulaRepository.deleteById(id);
    }
}
