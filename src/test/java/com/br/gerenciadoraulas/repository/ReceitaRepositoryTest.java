package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.Receita;
import com.br.gerenciadoraulas.model.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ReceitaRepositoryTest {

    @Autowired
    private ReceitaRepository receitaRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @Test
    void findByEventoId() {
        Evento e = new Evento();
        e.setTitulo("E");
        e.setTipo(TipoEvento.EVENTO);
        e.setStatus("AGENDADO");
        Evento savedEvento = eventoRepository.save(e);

        Receita r = new Receita();
        r.setDescricao("Receita teste");
        r.setEvento(savedEvento);
        r.setValor(new java.math.BigDecimal("50.0"));
        receitaRepository.save(r);

        assertEquals(1, receitaRepository.findByEventoId(savedEvento.getId()).size());
        assertTrue(receitaRepository.findByEventoId(999L).isEmpty());
    }
}
