package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.EventoDia;
import com.br.gerenciadoraulas.model.Participante;
import com.br.gerenciadoraulas.model.TipoEvento;
import com.br.gerenciadoraulas.repository.EventoDiaRepository;
import com.br.gerenciadoraulas.repository.EventoRepository;
import com.br.gerenciadoraulas.repository.ParticipanteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class EventoServiceTest {

    @Mock
    private EventoRepository eventoRepository;
    @Mock
    private EventoDiaRepository eventoDiaRepository;
    @Mock
    private ParticipanteRepository participanteRepository;

    @InjectMocks
    private EventoService service;

    @Test
    void listarTodos() {
        Evento e = new Evento();
        e.setId(1L);
        when(eventoRepository.findAll()).thenReturn(List.of(e));
        assertEquals(1, service.listarTodos().size());
    }

    @Test
    void buscarPorId() {
        Evento e = new Evento();
        e.setId(2L);
        when(eventoRepository.findById(2L)).thenReturn(Optional.of(e));
        assertTrue(service.buscarPorId(2L).isPresent());
        when(eventoRepository.findById(3L)).thenReturn(Optional.empty());
        assertTrue(service.buscarPorId(3L).isEmpty());
    }

    @Test
    void buscarPorTipo() {
        when(eventoRepository.findByTipo(TipoEvento.EVENTO)).thenReturn(List.of());
        assertTrue(service.buscarPorTipo(TipoEvento.EVENTO).isEmpty());
    }

    @Test
    void salvarVinculaFilhos() {
        Evento e = new Evento();
        e.setTitulo("T");
        EventoDia d = new EventoDia();
        d.setDataEvento(LocalDate.now());
        e.setDias(Set.of(d));
        Participante p = new Participante();
        p.setNome("Ana");
        e.setParticipantes(Set.of(p));

        when(eventoRepository.save(e)).thenReturn(e);
        Evento saved = service.salvar(e);
        assertEquals(1, saved.getParticipantesQtd());
        assertEquals(e, d.getEvento());
        assertEquals(e, p.getEvento());
    }

    @Test
    void salvarSemFilhos() {
        Evento e = new Evento();
        e.setTitulo("T");
        when(eventoRepository.save(e)).thenReturn(e);
        Evento saved = service.salvar(e);
        assertEquals(0, saved.getParticipantesQtd());
    }

    @Test
    void atualizarInexistenteLanca() {
        when(eventoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.atualizar(99L, new Evento()));
    }

    @Test
    void atualizarEscalares() {
        Evento existente = new Evento();
        existente.setId(1L);
        existente.setTitulo("old");
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of());

        Evento dados = new Evento();
        dados.setTitulo("novo");
        dados.setDescricao("desc");
        dados.setTipo(TipoEvento.VIAGEM);
        dados.setLocal("loc");
        dados.setStatus("OK");
        dados.setHorarioInicio("10:00");
        dados.setHorarioFim("12:00");

        Evento result = service.atualizar(1L, dados);
        assertEquals("novo", result.getTitulo());
        assertEquals("desc", result.getDescricao());
        assertEquals(TipoEvento.VIAGEM, result.getTipo());
    }

    @Test
    void atualizarRemoveParticipantesAusentes() {
        Evento existente = new Evento();
        existente.setId(1L);
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));

        Participante p1 = new Participante();
        p1.setId(10L);
        p1.setNome("Ana");
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of(p1));

        Evento dados = new Evento();
        dados.setParticipantes(Set.of()); // remove todos
        service.atualizar(1L, dados);
        verify(participanteRepository).deleteAll(anyList());
    }

    @Test
    void atualizarMantemParticipantePorNome() {
        Evento existente = new Evento();
        existente.setId(1L);
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));

        Participante existenteP = new Participante();
        existenteP.setId(10L);
        existenteP.setNome("Ana");
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of(existenteP));

        Participante p = new Participante();
        p.setNome("ana"); // mesmo nome case-insensitive
        Evento dados = new Evento();
        dados.setParticipantes(Set.of(p));
        service.atualizar(1L, dados);
        // mantido por nome -> nÃ£o deve criar nem deletar
        verify(participanteRepository, org.mockito.Mockito.never()).saveAll(anyList());
        verify(participanteRepository, org.mockito.Mockito.never()).deleteAll(anyList());
    }

    @Test
    void atualizarCriaNovoParticipante() {
        Evento existente = new Evento();
        existente.setId(1L);
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of());

        Participante p = new Participante();
        p.setNome("Novo");
        Evento dados = new Evento();
        dados.setParticipantes(Set.of(p));
        service.atualizar(1L, dados);
        verify(participanteRepository).saveAll(anyList());
    }

    @Test
    void atualizarIgnoraParticipanteSemNome() {
        Evento existente = new Evento();
        existente.setId(1L);
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of());

        Participante p = new Participante();
        p.setNome("   ");
        Evento dados = new Evento();
        dados.setParticipantes(Set.of(p));
        service.atualizar(1L, dados);
        verify(participanteRepository, org.mockito.Mockito.never()).saveAll(anyList());
    }

    @Test
    void atualizarSubstituiDias() {
        Evento existente = new Evento();
        existente.setId(1L);
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of());

        EventoDia d = new EventoDia();
        d.setDataEvento(LocalDate.now());
        d.setHorario("10:00");
        Evento dados = new Evento();
        dados.setDias(Set.of(d));
        service.atualizar(1L, dados);
        verify(eventoDiaRepository).deleteByEventoId(1L);
        verify(eventoDiaRepository).saveAll(anyList());
    }

    @Test
    void atualizarSemDiasNaoTocaDias() {
        Evento existente = new Evento();
        existente.setId(1L);
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of());

        Evento dados = new Evento();
        // dias null -> nÃ£o substitui
        service.atualizar(1L, dados);
        verify(eventoDiaRepository, org.mockito.Mockito.never()).deleteByEventoId(any());
    }

    @Test
    void atualizarAtualizaParticipantesQtd() {
        Evento existente = new Evento();
        existente.setId(1L);
        when(eventoRepository.findById(1L)).thenReturn(Optional.of(existente));
        Participante p = new Participante();
        p.setId(5L);
        p.setNome("Ana");
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of(p));

        Evento dados = new Evento();
        dados.setParticipantes(Set.of(p));
        service.atualizar(1L, dados);
        assertEquals(1, existente.getParticipantesQtd());
    }

    @Test
    void deletar() {
        service.deletar(5L);
        verify(eventoRepository).deleteById(5L);
    }
}

