package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.AlunoDTO;
import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.repository.AlunoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AlunoService {

    private final AlunoRepository alunoRepository;

    public AlunoService(AlunoRepository alunoRepository) {
        this.alunoRepository = alunoRepository;
    }

    public List<AlunoDTO> listarTodos() {
        return alunoRepository.findAll()
                .stream()
                .map(AlunoDTO::new)
                .collect(Collectors.toList());
    }

    public List<AlunoDTO> listarNaoMatriculados(Long cicloId) {
        return alunoRepository.findAlunosNaoMatriculados(cicloId)
                .stream()
                .map(AlunoDTO::new)
                .collect(Collectors.toList());
    }

    public AlunoDTO salvar(Aluno aluno) {
        Aluno saved = this.alunoRepository.save(aluno);
        return new AlunoDTO(saved);
    }



}
