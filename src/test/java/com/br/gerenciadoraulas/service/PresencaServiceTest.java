package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.PresencaDTO;
import com.br.gerenciadoraulas.model.Aula;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Presenca;
import com.br.gerenciadoraulas.repository.AulaRepository;
import com.br.gerenciadoraulas.repository.MatriculaRepository;
import com.br.gerenciadoraulas.repository.PresencaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PresencaServiceTest {

    @Mock
    private PresencaRepository presencaRepository;
    @Mock
    private AulaRepository aulaRepository;
    @Mock
    private MatriculaRepository matriculaRepository;

    @InjectMocks
    private PresencaService service;

    @Test
    void listarTodas() {
        when(presencaRepository.findAll()).thenReturn(List.of());
        assertTrue(service.listarTodas().isEmpty());
    }

    @Test
    void buscarPorId() {
        Presenca p = new Presenca();
        p.setId(1L);
        when(presencaRepository.findById(1L)).thenReturn(Optional.of(p));
        assertTrue(service.buscarPorId(1L).isPresent());
    }

    @Test
    void salvarSemAulaLanca() {
        PresencaDTO dto = new PresencaDTO();
        dto.setMatriculaId(1L);
        assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
    }

    @Test
    void salvarComAulaInexistenteLanca() {
        PresencaDTO dto = new PresencaDTO();
        dto.setAulaId(1L);
        dto.setMatriculaId(2L);
        when(aulaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
    }

    @Test
    void salvarAulaNaoHojeLanca() {
        PresencaDTO dto = new PresencaDTO();
        dto.setAulaId(1L);
        dto.setMatriculaId(2L);
        Aula aula = new Aula();
        aula.setId(1L);
        aula.setData(LocalDateTime.now().minusDays(1));
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        assertThrows(IllegalStateException.class, () -> service.salvar(dto));
    }

    @Test
    void salvarMatriculaInativaLanca() {
        PresencaDTO dto = new PresencaDTO();
        dto.setAulaId(1L);
        dto.setMatriculaId(2L);
        Aula aula = new Aula();
        aula.setId(1L);
        aula.setData(LocalDateTime.now());
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        Matricula mat = new Matricula();
        mat.setId(2L);
        mat.setFlAtivo(false);
        when(matriculaRepository.findById(2L)).thenReturn(Optional.of(mat));
        assertThrows(IllegalStateException.class, () -> service.salvar(dto));
    }

    @Test
    void salvarMatriculaInexistenteLanca() {
        PresencaDTO dto = new PresencaDTO();
        dto.setAulaId(1L);
        dto.setMatriculaId(2L);
        Aula aula = new Aula();
        aula.setId(1L);
        aula.setData(LocalDateTime.now());
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        when(matriculaRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.salvar(dto));
    }

    @Test
    void deletarInexistenteLanca() {
        when(presencaRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.deletar(99L));
    }

    @Test
    void deletarAulaPassadaLanca() {
        Presenca p = new Presenca();
        p.setId(1L);
        Aula aula = new Aula();
        aula.setData(LocalDateTime.now().minusDays(1));
        p.setAula(aula);
        when(presencaRepository.findById(1L)).thenReturn(Optional.of(p));
        assertThrows(IllegalStateException.class, () -> service.deletar(1L));
    }

    @Test
    void deletarMatriculaInativaLanca() {
        Presenca p = new Presenca();
        p.setId(1L);
        Aula aula = new Aula();
        aula.setData(LocalDateTime.now());
        p.setAula(aula);
        Matricula m = new Matricula();
        m.setFlAtivo(false);
        p.setMatricula(m);
        when(presencaRepository.findById(1L)).thenReturn(Optional.of(p));
        assertThrows(IllegalStateException.class, () -> service.deletar(1L));
    }

    @Test
    void deletarSucesso() {
        Presenca p = new Presenca();
        p.setId(1L);
        when(presencaRepository.findById(1L)).thenReturn(Optional.of(p));
        service.deletar(1L);
        verify(presencaRepository).deleteById(1L);
    }

    @Test
    void deletarPorMatriculaEAulaAulaInexistenteLanca() {
        when(aulaRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.deletarPorMatriculaEAula(2L, 1L));
    }

    @Test
    void deletarPorMatriculaEAulaAulaPassadaLanca() {
        Aula aula = new Aula();
        aula.setData(LocalDateTime.now().minusDays(1));
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        assertThrows(IllegalStateException.class, () -> service.deletarPorMatriculaEAula(2L, 1L));
    }

    @Test
    void deletarPorMatriculaEAulaMatriculaInexistenteLanca() {
        Aula aula = new Aula();
        aula.setData(LocalDateTime.now());
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        when(matriculaRepository.findById(2L)).thenReturn(Optional.empty());
        assertThrows(IllegalArgumentException.class, () -> service.deletarPorMatriculaEAula(2L, 1L));
    }

    @Test
    void deletarPorMatriculaEAulaMatriculaInativaLanca() {
        Aula aula = new Aula();
        aula.setData(LocalDateTime.now());
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        Matricula m = new Matricula();
        m.setFlAtivo(false);
        when(matriculaRepository.findById(2L)).thenReturn(Optional.of(m));
        assertThrows(IllegalStateException.class, () -> service.deletarPorMatriculaEAula(2L, 1L));
    }

    @Test
    void deletarPorMatriculaEAulaSucesso() {
        Aula aula = new Aula();
        aula.setData(LocalDateTime.now());
        when(aulaRepository.findById(1L)).thenReturn(Optional.of(aula));
        Matricula m = new Matricula();
        m.setFlAtivo(true);
        when(matriculaRepository.findById(2L)).thenReturn(Optional.of(m));
        service.deletarPorMatriculaEAula(2L, 1L);
        verify(presencaRepository).deleteByMatriculaIdAndAulaId(2L, 1L);
    }
}
