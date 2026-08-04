package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.repository.MatriculaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;

    public MatriculaService(MatriculaRepository matriculaRepository) {
        this.matriculaRepository = matriculaRepository;
    }

    public List<Matricula> listarTodas() {
        return matriculaRepository.findAll();
    }

    public Optional<Matricula> buscarPorId(Long id) {
        return matriculaRepository.findById(id);
    }

    public Matricula salvar(Matricula matricula) {
        return matriculaRepository.save(matricula);
    }

    public void deletar(Long id) {
        matriculaRepository.deleteById(id);
    }

    public List<Matricula> listarPorProgramaAula(Long programaAulaId) {
        return matriculaRepository.findByProgramaAulaId(programaAulaId);
    }
}
