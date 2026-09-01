package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.PresencaDTO;
import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Presenca;
import com.br.gerenciadoraulas.repository.AulaRepository;
import com.br.gerenciadoraulas.repository.MatriculaRepository;
import com.br.gerenciadoraulas.repository.PresencaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PresencaService {

    private final PresencaRepository presencaRepository;
    private final AulaRepository aulaRepository;
    private final MatriculaRepository matriculaRepository;

    public PresencaService(PresencaRepository presencaRepository,
                           AulaRepository aulaRepository,
                           MatriculaRepository matriculaRepository) {
        this.presencaRepository = presencaRepository;
        this.aulaRepository = aulaRepository;
        this.matriculaRepository = matriculaRepository;
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

    private PresencaDTO salvar(Presenca presenca) {
        Presenca saved = presencaRepository.save(presenca);
        return new PresencaDTO(saved);
    }

    public PresencaDTO salvar(PresencaDTO presencaDTO) {
        if (presencaDTO.getAulaId() == null || presencaDTO.getMatriculaId() == null) {
            throw new IllegalArgumentException("Aula e Matrícula são obrigatórios para registrar presença.");
        }

        Aula aula = aulaRepository.findById(presencaDTO.getAulaId())
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada."));

        if (aula.getData() == null || !aula.getData().toLocalDate().isEqual(LocalDate.now())) {
            throw new IllegalStateException("Só é possível alterar a presença em aulas do dia atual.");
        }

        Matricula matricula = matriculaRepository.findById(presencaDTO.getMatriculaId())
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada."));

        if (matricula.getFlAtivo() != null && !matricula.getFlAtivo()) {
            throw new IllegalStateException("Não é possível alterar a presença de um aluno com matrícula inativa.");
        }

        Presenca presenca = presencaDTO.generatePresenca();
        return salvar(presenca);
    }

    public void deletar(Long id) {
        Presenca presenca = presencaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Presença não encontrada."));

        if (presenca.getAula() != null && presenca.getAula().getData() != null) {
            if (!presenca.getAula().getData().toLocalDate().isEqual(LocalDate.now())) {
                throw new IllegalStateException("Só é possível alterar a presença em aulas do dia atual.");
            }
        }

        if (presenca.getMatricula() != null && presenca.getMatricula().getFlAtivo() != null && !presenca.getMatricula().getFlAtivo()) {
            throw new IllegalStateException("Não é possível alterar a presença de um aluno com matrícula inativa.");
        }

        presencaRepository.deleteById(id);
    }

    @Transactional
    public void deletarPorMatriculaEAula(Long matriculaId, Long aulaId) {
        Aula aula = aulaRepository.findById(aulaId)
                .orElseThrow(() -> new IllegalArgumentException("Aula não encontrada."));

        if (aula.getData() == null || !aula.getData().toLocalDate().isEqual(LocalDate.now())) {
            throw new IllegalStateException("Só é possível alterar a presença em aulas do dia atual.");
        }

        Matricula matricula = matriculaRepository.findById(matriculaId)
                .orElseThrow(() -> new IllegalArgumentException("Matrícula não encontrada."));

        if (matricula.getFlAtivo() != null && !matricula.getFlAtivo()) {
            throw new IllegalStateException("Não é possível alterar a presença de um aluno com matrícula inativa.");
        }

        presencaRepository.deleteByMatriculaIdAndAulaId(matriculaId, aulaId);
    }
}