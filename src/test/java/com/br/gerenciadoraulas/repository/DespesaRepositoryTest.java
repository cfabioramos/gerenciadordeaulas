package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Despesa;
import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class DespesaRepositoryTest {

    @Autowired
    private DespesaRepository despesaRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @Test
    void findByEventoId() {
        Evento e = new Evento();
        e.setTitulo("E");
        e.setTipo(TipoEvento.EVENTO);
        e.setStatus("AGENDADO");
        Evento savedEvento = eventoRepository.save(e);

        Despesa d = new Despesa();
        d.setDescricao("Despesa teste");
        d.setEvento(savedEvento);
        d.setValor(new java.math.BigDecimal("10.0"));
        despesaRepository.save(d);

        List<Despesa> result = despesaRepository.findByEventoId(savedEvento.getId());
        assertEquals(1, result.size());
        assertTrue(despesaRepository.findByEventoId(999L).isEmpty());
    }
}
