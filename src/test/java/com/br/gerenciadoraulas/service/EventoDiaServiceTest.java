package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.EventoDia;
import com.br.gerenciadoraulas.repository.EventoDiaRepository;
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
class EventoDiaServiceTest {
    @Mock
    private EventoDiaRepository eventoDiaRepository;
    @InjectMocks
    private EventoDiaService service;

    @Test
    void listarPorEvento() {
        when(eventoDiaRepository.findByEventoId(1L)).thenReturn(List.of(new EventoDia()));
        assertEquals(1, service.listarPorEvento(1L).size());
    }

    @Test
    void salvar() {
        EventoDia d = new EventoDia();
        when(eventoDiaRepository.save(d)).thenReturn(d);
        assertEquals(d, service.salvar(d));
    }

    @Test
    void deletar() {
        service.deletar(5L);
        verify(eventoDiaRepository).deleteById(5L);
    }
}
