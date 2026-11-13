package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Despesa;
import com.br.gerenciadoraulas.repository.DespesaRepository;
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
class DespesaServiceTest {
    @Mock
    private DespesaRepository despesaRepository;
    @InjectMocks
    private DespesaService service;

    @Test
    void listarPorEvento() {
        when(despesaRepository.findByEventoId(1L)).thenReturn(List.of(new Despesa()));
        assertEquals(1, service.listarPorEvento(1L).size());
    }

    @Test
    void salvar() {
        Despesa d = new Despesa();
        when(despesaRepository.save(d)).thenReturn(d);
        assertEquals(d, service.salvar(d));
    }

    @Test
    void deletar() {
        service.deletar(5L);
        verify(despesaRepository).deleteById(5L);
    }
}
