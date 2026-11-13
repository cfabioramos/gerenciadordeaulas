package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Receita;
import com.br.gerenciadoraulas.repository.ReceitaRepository;
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
class ReceitaServiceTest {
    @Mock
    private ReceitaRepository receitaRepository;
    @InjectMocks
    private ReceitaService service;

    @Test
    void listarPorEvento() {
        when(receitaRepository.findByEventoId(1L)).thenReturn(List.of(new Receita()));
        assertEquals(1, service.listarPorEvento(1L).size());
    }

    @Test
    void salvar() {
        Receita r = new Receita();
        when(receitaRepository.save(r)).thenReturn(r);
        assertEquals(r, service.salvar(r));
    }

    @Test
    void deletar() {
        service.deletar(5L);
        verify(receitaRepository).deleteById(5L);
    }
}
