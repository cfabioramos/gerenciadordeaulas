package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.CicloDTO;
import com.br.gerenciadoraulas.model.Ciclo;
import com.br.gerenciadoraulas.repository.CicloRepository;
import com.br.gerenciadoraulas.repository.ProgramaAulaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
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
class CicloServiceTest {

    @Mock
    private CicloRepository cicloRepository;
    @Mock
    private ProgramaAulaRepository programaAulaRepository;

    @InjectMocks
    private CicloService service;

    @Test
    void listarTodas() {
        Ciclo c = new Ciclo();
        c.setId(1L);
        when(cicloRepository.findAll(org.mockito.ArgumentMatchers.any(org.springframework.data.domain.Sort.class))).thenReturn(List.of(c));
        assertEquals(1, service.listarTodas().size());
    }

    @Test
    void buscarPorId() {
        Ciclo c = new Ciclo();
        c.setId(2L);
        when(cicloRepository.findById(2L)).thenReturn(Optional.of(c));
        assertTrue(service.buscarPorId(2L).isPresent());
        when(cicloRepository.findById(3L)).thenReturn(Optional.empty());
        assertTrue(service.buscarPorId(3L).isEmpty());
    }

    @Test
    void salvar() {
        Ciclo c = new Ciclo();
        c.setNome("Ciclo");
        Ciclo saved = new Ciclo();
        saved.setId(5L);
        saved.setNome("Ciclo");
        when(cicloRepository.save(c)).thenReturn(saved);
        CicloDTO dto = service.salvar(c);
        assertEquals(5L, dto.getId());
    }

    @Test
    void atualizarExistente() {
        Ciclo existing = new Ciclo();
        existing.setId(1L);
        Ciclo dados = new Ciclo();
        dados.setNome("Novo");
        dados.setDataInicio(LocalDate.now());
        dados.setDataFim(LocalDate.now().plusDays(1));
        when(cicloRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(cicloRepository.save(existing)).thenReturn(existing);
        assertTrue(service.atualizar(1L, dados).isPresent());
        assertEquals("Novo", existing.getNome());
    }

    @Test
    void atualizarInexistente() {
        when(cicloRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(service.atualizar(99L, new Ciclo()).isEmpty());
    }

    @Test
    void deletarComProgramasLanca() {
        when(programaAulaRepository.existsByCicloId(1L)).thenReturn(true);
        assertThrows(IllegalStateException.class, () -> service.deletar(1L));
        verify(cicloRepository, never()).deleteById(any());
    }

    @Test
    void deletarSemProgramasDeleta() {
        when(programaAulaRepository.existsByCicloId(1L)).thenReturn(false);
        service.deletar(1L);
        verify(cicloRepository).deleteById(1L);
    }
}
