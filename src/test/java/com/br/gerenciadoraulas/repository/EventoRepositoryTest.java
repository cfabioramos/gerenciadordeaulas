package com.br.gerenciadoraulas.repository;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.TipoEvento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
class EventoRepositoryTest {

    @Autowired
    private EventoRepository eventoRepository;

    @Test
    void saveEFindById() {
        Evento e = new Evento();
        e.setTitulo("Workshop");
        e.setTipo(TipoEvento.EVENTO);
        e.setStatus("AGENDADO");
        Evento saved = eventoRepository.save(e);
        assertTrue(eventoRepository.findById(saved.getId()).isPresent());
    }

    @Test
    void findByTipo() {
        Evento e = new Evento();
        e.setTitulo("Viagem Teste");
        e.setTipo(TipoEvento.VIAGEM);
        e.setStatus("AGENDADO");
        eventoRepository.save(e);

        List<Evento> viagens = eventoRepository.findByTipo(TipoEvento.VIAGEM);
        assertFalse(viagens.isEmpty());
        assertTrue(viagens.stream().allMatch(ev -> ev.getTipo() == TipoEvento.VIAGEM));
    }

    @Test
    void findByTituloContainingIgnoreCase() {
        Evento e = new Evento();
        e.setTitulo("Workshop de Forró");
        e.setTipo(TipoEvento.EVENTO);
        e.setStatus("AGENDADO");
        eventoRepository.save(e);

        assertTrue(eventoRepository.findByTituloContainingIgnoreCase("workshop").isPresent());
        assertTrue(eventoRepository.findByTituloContainingIgnoreCase("FORRÓ").isPresent());
        assertTrue(eventoRepository.findByTituloContainingIgnoreCase("inexistente").isEmpty());
    }

    @Test
    void listarTodos() {
        Evento e = new Evento();
        e.setTitulo("E1");
        e.setTipo(TipoEvento.EVENTO);
        e.setStatus("AGENDADO");
        eventoRepository.save(e);
        assertEquals(1, eventoRepository.findAll().size());
    }
}
