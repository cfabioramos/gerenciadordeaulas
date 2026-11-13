package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.ProgramaAulaDTO;
import com.br.gerenciadoraulas.model.ProgramaAula;
import com.br.gerenciadoraulas.repository.AulaRepository;
import com.br.gerenciadoraulas.repository.ProgramaAulaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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
class ProgramaAulaServiceTest {

    @Mock
    private ProgramaAulaRepository programaAulaRepository;
    @Mock
    private AulaRepository aulaRepository;

    @InjectMocks
    private ProgramaAulaService service;

    @Test
    void listarTodas() {
        when(programaAulaRepository.findAll(org.mockito.ArgumentMatchers.any(org.springframework.data.domain.Sort.class))).thenReturn(List.of());
        assertTrue(service.listarTodas().isEmpty());
    }

    @Test
    void buscarPorId() {
        ProgramaAula p = new ProgramaAula();
        p.setId(1L);
        when(programaAulaRepository.findById(1L)).thenReturn(Optional.of(p));
        assertTrue(service.buscarPorId(1L).isPresent());
        when(programaAulaRepository.findById(2L)).thenReturn(Optional.empty());
        assertTrue(service.buscarPorId(2L).isEmpty());
    }

    @Test
    void salvar() {
        ProgramaAula p = new ProgramaAula();
        ProgramaAula saved = new ProgramaAula();
        saved.setId(3L);
        when(programaAulaRepository.save(p)).thenReturn(saved);
        ProgramaAulaDTO dto = service.salvar(p);
        assertEquals(3L, dto.getId());
    }

    @Test
    void atualizarExistente() {
        ProgramaAula existing = new ProgramaAula();
        existing.setId(1L);
        ProgramaAula dados = new ProgramaAula();
        dados.setNome("Novo");
        when(programaAulaRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(programaAulaRepository.save(existing)).thenReturn(existing);
        assertTrue(service.atualizar(1L, dados).isPresent());
        assertEquals("Novo", existing.getNome());
    }

    @Test
    void atualizarInexistente() {
        when(programaAulaRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(service.atualizar(99L, new ProgramaAula()).isEmpty());
    }

    @Test
    void deletarComAulasLanca() {
        when(aulaRepository.existsByProgramaAulaId(1L)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> service.deletar(1L));
        verify(programaAulaRepository, never()).deleteById(any());
    }

    @Test
    void deletarSemAulasDeleta() {
        when(aulaRepository.existsByProgramaAulaId(1L)).thenReturn(false);
        service.deletar(1L);
        verify(programaAulaRepository).deleteById(1L);
    }

    @Test
    void listarPorCiclo() {
        when(programaAulaRepository.findByCicloId(5L)).thenReturn(List.of());
        assertTrue(service.listarPorCiclo(5L).isEmpty());
    }
}
