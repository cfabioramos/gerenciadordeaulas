package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.MatriculaDTO;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.repository.MatriculaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatriculaServiceTest {

    @Mock
    private MatriculaRepository matriculaRepository;

    @InjectMocks
    private MatriculaService service;

    @Test
    void listarTodas() {
        when(matriculaRepository.findAll()).thenReturn(List.of(new Matricula()));
        assertEquals(1, service.listarTodas().size());
    }

    @Test
    void buscarPorId() {
        Matricula m = new Matricula();
        m.setId(1L);
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(m));
        assertTrue(service.buscarPorId(1L).isPresent());
    }

    @Test
    void salvar() {
        Matricula m = new Matricula();
        Matricula saved = new Matricula();
        saved.setId(5L);
        when(matriculaRepository.save(m)).thenReturn(saved);
        MatriculaDTO dto = service.salvar(m);
        assertEquals(5L, dto.getId());
    }

    @Test
    void deletar() {
        service.deletar(7L);
        verify(matriculaRepository).deleteById(7L);
    }

    @Test
    void atualizarStatusExistente() {
        Matricula m = new Matricula();
        m.setId(1L);
        m.setFlAtivo(true);
        when(matriculaRepository.findById(1L)).thenReturn(Optional.of(m));
        service.atualizarStatus(1L, false);
        assertEquals(false, m.getFlAtivo());
        verify(matriculaRepository).save(m);
    }

    @Test
    void atualizarStatusInexistenteNaoSalva() {
        when(matriculaRepository.findById(99L)).thenReturn(Optional.empty());
        service.atualizarStatus(99L, true);
        verify(matriculaRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    void listarPorProgramaAula() {
        when(matriculaRepository.findByProgramaAulaId(3L)).thenReturn(List.of());
        assertTrue(service.listarPorProgramaAula(3L).isEmpty());
    }

    @Test
    void listarPorAluno() {
        when(matriculaRepository.findByAlunoId(4L)).thenReturn(List.of());
        assertTrue(service.listarPorAluno(4L).isEmpty());
    }

    @Test
    void consultarMatriculasPorAula() {
        when(matriculaRepository.consultarMatriculasPorAula(2L)).thenReturn(List.of());
        assertTrue(service.consultarMatriculasPorAula(2L).isEmpty());
    }
}
