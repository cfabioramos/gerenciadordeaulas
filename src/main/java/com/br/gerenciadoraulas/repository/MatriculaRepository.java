package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {
    List<Matricula> findByProgramaAulaId(Long programaAulaId);
}