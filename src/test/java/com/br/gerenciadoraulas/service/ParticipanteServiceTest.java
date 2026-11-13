package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Participante;
import com.br.gerenciadoraulas.repository.ParticipanteRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ParticipanteServiceTest {
    @Mock
    private ParticipanteRepository participanteRepository;
    @InjectMocks
    private ParticipanteService service;

    @Test
    void listarPorEvento() {
        when(participanteRepository.findByEventoId(1L)).thenReturn(List.of(new Participante()));
        assertEquals(1, service.listarPorEvento(1L).size());
    }

    @Test
    void salvar() {
        Participante p = new Participante();
        when(participanteRepository.save(p)).thenReturn(p);
        assertEquals(p, service.salvar(p));
    }

    @Test
    void deletar() {
        service.deletar(5L);
        verify(participanteRepository).deleteById(5L);
    }
}
