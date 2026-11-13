package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.AulaDTO;
import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.repository.AulaRepository;
import com.br.gerenciadoraulas.repository.PresencaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AulaServiceTest {

    @Mock
    private AulaRepository aulaRepository;
    @Mock
    private PresencaRepository presencaRepository;

    @InjectMocks
    private AulaService service;

    @Test
    void listarTodas() {
        Aula a = new Aula();
        a.setId(1L);
        when(aulaRepository.findAll()).thenReturn(List.of(a));
        assertEquals(1, service.listarTodas().size());
    }

    @Test
    void findByData() {
        when(aulaRepository.findByData(any())).thenReturn(List.of());
        assertTrue(service.findByData(LocalDateTime.now()).isEmpty());
    }

    @Test
    void findByDataBetween() {
        when(aulaRepository.findByDataBetween(any(), any())).thenReturn(List.of());
        assertTrue(service.findByDataBetween(LocalDateTime.now(), LocalDateTime.now().plusDays(1)).isEmpty());
    }

    @Test
    void listarPorProgramaAula() {
        when(aulaRepository.findByProgramaAulaId(5L)).thenReturn(List.of());
        assertTrue(service.listarPorProgramaAula(5L).isEmpty());
    }

    @Test
    void buscarPorId() {
        Aula a = new Aula();
        a.setId(2L);
        when(aulaRepository.findById(2L)).thenReturn(Optional.of(a));
        assertTrue(service.buscarPorId(2L).isPresent());
        when(aulaRepository.findById(3L)).thenReturn(Optional.empty());
        assertTrue(service.buscarPorId(3L).isEmpty());
    }

    @Test
    void salvar() {
        Aula a = new Aula();
        a.setNome("Aula 1");
        Aula saved = new Aula();
        saved.setId(10L);
        saved.setNome("Aula 1");
        when(aulaRepository.save(a)).thenReturn(saved);
        AulaDTO dto = service.salvar(a);
        assertEquals(10L, dto.getId());
    }

    @Test
    void deletarComPresencaLanca() {
        when(presencaRepository.existsByAulaId(1L)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> service.deletar(1L));
        verify(aulaRepository, never()).deleteById(any());
    }

    @Test
    void deletarSemPresencaDeleta() {
        when(presencaRepository.existsByAulaId(1L)).thenReturn(false);
        service.deletar(1L);
        verify(aulaRepository).deleteById(1L);
    }

    @Test
    void atualizarExistente() {
        Aula existing = new Aula();
        existing.setId(1L);
        existing.setNome("old");
        Aula updated = new Aula();
        updated.setNome("new");
        updated.setData(LocalDateTime.now());
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(aulaRepository.save(existing)).thenReturn(existing);
        assertTrue(service.atualizar(1L, updated).isPresent());
        assertEquals("new", existing.getNome());
    }

    @Test
    void atualizarInexistente() {
        when(aulaRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(service.atualizar(99L, new Aula()).isEmpty());
    }
}
