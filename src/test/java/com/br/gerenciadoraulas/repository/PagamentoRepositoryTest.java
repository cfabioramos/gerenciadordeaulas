package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Pagamento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PagamentoRepositoryTest {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Test
    void saveEFind() {
        Pagamento p = new Pagamento();
        p.setValor(new BigDecimal("100.00"));
        p.setData(LocalDate.now());
        Pagamento saved = pagamentoRepository.save(p);
        assertTrue(pagamentoRepository.findById(saved.getId()).isPresent());
        assertEquals(1, pagamentoRepository.findAll().size());
    }

    @Test
    void findByAlunoIdVazio() {
        assertTrue(pagamentoRepository.findByAlunoId(999L).isEmpty());
    }
}
