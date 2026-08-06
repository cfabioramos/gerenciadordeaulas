package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.CicloDTO;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.repository.CicloRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CicloService {

    private final CicloRepository cicloRepository;

    public CicloService(CicloRepository cicloRepository) {
        this.cicloRepository = cicloRepository;
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
        cicloRepository.deleteById(id);
    }
}
