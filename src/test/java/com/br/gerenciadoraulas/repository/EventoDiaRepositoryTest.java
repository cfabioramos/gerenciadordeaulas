package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.EventoDia;
import com.br.gerenciadoraulas.model.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EventoDiaRepositoryTest {

    @Autowired
    private EventoDiaRepository eventoDiaRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @Test
    void findByEventoIdEDeleteByEventoId() {
        Evento e = new Evento();
        e.setTitulo("E");
        e.setTipo(TipoEvento.EVENTO);
        e.setStatus("AGENDADO");
        Evento savedEvento = eventoRepository.save(e);

        EventoDia d = new EventoDia();
        d.setEvento(savedEvento);
        d.setDataEvento(LocalDate.now());
        d.setHorario("10:00");
        eventoDiaRepository.save(d);

        assertEquals(1, eventoDiaRepository.findByEventoId(savedEvento.getId()).size());
        eventoDiaRepository.deleteByEventoId(savedEvento.getId());
        assertTrue(eventoDiaRepository.findByEventoId(savedEvento.getId()).isEmpty());
    }
}
