package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.Participante;
import com.br.gerenciadoraulas.model.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class ParticipanteRepositoryTest {

    @Autowired
    private ParticipanteRepository participanteRepository;
    @Autowired
    private EventoRepository eventoRepository;

    @Test
    void findByEventoId() {
        Evento e = new Evento();
        e.setTitulo("E");
        e.setTipo(TipoEvento.EVENTO);
        e.setStatus("AGENDADO");
        Evento savedEvento = eventoRepository.save(e);

        Participante p = new Participante();
        p.setNome("Ana");
        p.setEvento(savedEvento);
        participanteRepository.save(p);

        assertEquals(1, participanteRepository.findByEventoId(savedEvento.getId()).size());
        assertTrue(participanteRepository.findByEventoId(999L).isEmpty());
    }
}
