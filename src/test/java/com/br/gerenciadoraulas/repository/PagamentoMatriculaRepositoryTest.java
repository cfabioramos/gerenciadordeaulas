package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Pagamento;
import com.br.gerenciadoraulas.model.PagamentoMatricula;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class PagamentoMatriculaRepositoryTest {

    @Autowired
    private PagamentoMatriculaRepository pagamentoMatriculaRepository;
    @Autowired
    private PagamentoRepository pagamentoRepository;
    @Autowired
    private MatriculaRepository matriculaRepository;

    @Test
    void saveEFind() {
        Pagamento p = pagamentoRepository.save(new Pagamento());
        Matricula m = matriculaRepository.save(new Matricula());

        PagamentoMatricula pm = new PagamentoMatricula();
        pm.setPagamento(p);
        pm.setMatricula(m);
        pm.setValorMensalidadeDia(new java.math.BigDecimal("10.00"));
        PagamentoMatricula saved = pagamentoMatriculaRepository.save(pm);

        assertTrue(pagamentoMatriculaRepository.findById(saved.getId()).isPresent());
        assertEquals(1, pagamentoMatriculaRepository.findAll().size());
    }
}
