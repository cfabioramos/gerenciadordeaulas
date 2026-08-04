package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Long> {

    // Busca todas as aulas em uma data específica
    List<Aula> findByData(LocalDate data);

    // Se quiser buscar por intervalo de datas:
    List<Aula> findByDataBetween(LocalDate inicio, LocalDate fim);

}