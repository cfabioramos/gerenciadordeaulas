package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.ProgramaAula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProgramaAulaRepository extends JpaRepository<ProgramaAula, Long> {

    // Retorna a lista de Programas de Aula pelo id do Ciclo associado
    List<ProgramaAula> findByCicloId(Long cicloId);
}