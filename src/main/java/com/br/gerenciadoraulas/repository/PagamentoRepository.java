package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Pagamento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long> {
    @Query("SELECT DISTINCT p FROM Pagamento p JOIN p.pagamentoMatriculas pm JOIN pm.matricula m WHERE m.aluno.id = :alunoId")
    List<Pagamento> findByAlunoId(@Param("alunoId") Long alunoId);
}
