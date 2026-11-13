package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.CadastroPagamentoDTO;
import com.br.gerenciadoraulas.dto.EditarPagamentoDTO;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Pagamento;
import com.br.gerenciadoraulas.model.PagamentoMatricula;
import com.br.gerenciadoraulas.repository.MatriculaRepository;
import com.br.gerenciadoraulas.repository.PagamentoMatriculaRepository;
import com.br.gerenciadoraulas.repository.PagamentoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PagamentoServiceTest {

    @Mock
    private PagamentoRepository pagamentoRepository;
    @Mock
    private PagamentoMatriculaRepository pagamentoMatriculaRepository;
    @Mock
    private MatriculaRepository matriculaRepository;

    @InjectMocks
    private PagamentoService service;

    @Test
    void salvarSemMatriculas() {
        CadastroPagamentoDTO dto = new CadastroPagamentoDTO();
        dto.setValor(new BigDecimal("100.0"));
        dto.setData(LocalDate.now());
        dto.setMatriculaIds(null);

        Pagamento saved = new Pagamento();
        saved.setId(1L);
        saved.setValor(new BigDecimal("100.0"));
        saved.setData(LocalDate.now());
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(saved);

        var result = service.salvar(dto);
        assertEquals(1L, result.getId());
    }

    @Test
    void salvarComMatriculaExistente() {
        CadastroPagamentoDTO dto = new CadastroPagamentoDTO();
        dto.setValor(new BigDecimal("50.0"));
        dto.setData(LocalDate.now());
        dto.setMatriculaIds(List.of(2L));

        Matricula m = new Matricula();
        m.setId(2L);
        when(matriculaRepository.findById(2L)).thenReturn(Optional.of(m));

        Pagamento saved = new Pagamento();
        saved.setId(3L);
        saved.setValor(new BigDecimal("50.0"));
        saved.setData(LocalDate.now());
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(saved);

        var result = service.salvar(dto);
        assertEquals(3L, result.getId());
        verify(pagamentoMatriculaRepository).save(any(PagamentoMatricula.class));
    }

    @Test
    void salvarComMatriculaInexistenteIgnora() {
        CadastroPagamentoDTO dto = new CadastroPagamentoDTO();
        dto.setValor(new BigDecimal("50.0"));
        dto.setData(LocalDate.now());
        dto.setMatriculaIds(List.of(99L));

        when(matriculaRepository.findById(99L)).thenReturn(Optional.empty());

        Pagamento saved = new Pagamento();
        saved.setId(4L);
        saved.setValor(new BigDecimal("50.0"));
        saved.setData(LocalDate.now());
        when(pagamentoRepository.save(any(Pagamento.class))).thenReturn(saved);

        service.salvar(dto);
        verify(pagamentoMatriculaRepository, org.mockito.Mockito.never()).save(any());
    }

    @Test
    void atualizarExistente() {
        Pagamento p = new Pagamento();
        p.setId(1L);
        when(pagamentoRepository.findById(1L)).thenReturn(Optional.of(p));
        when(pagamentoRepository.save(p)).thenReturn(p);

        EditarPagamentoDTO dto = new EditarPagamentoDTO();
        dto.setValor(new BigDecimal("200.0"));
        dto.setData(LocalDate.now());
        assertTrue(service.atualizar(1L, dto).isPresent());
        assertEquals(new BigDecimal("200.0"), p.getValor());
    }

    @Test
    void atualizarInexistente() {
        when(pagamentoRepository.findById(99L)).thenReturn(Optional.empty());
        assertTrue(service.atualizar(99L, new EditarPagamentoDTO()).isEmpty());
    }

    @Test
    void listarPorAluno() {
        when(pagamentoRepository.findByAlunoId(1L)).thenReturn(List.of());
        assertTrue(service.listarPorAluno(1L).isEmpty());
    }

    @Test
    void listarTodosSemFiltro() {
        Pagamento p = new Pagamento();
        p.setId(1L);
        p.setData(LocalDate.now());
        when(pagamentoRepository.findAll(org.mockito.ArgumentMatchers.any(org.springframework.data.domain.Sort.class))).thenReturn(List.of(p));
        assertEquals(1, service.listarTodos(null, null, null, null).size());
    }

    @Test
    void listarTodosFiltraPorData() {
        Pagamento p = new Pagamento();
        p.setId(1L);
        p.setData(LocalDate.of(2026, 1, 1));
        when(pagamentoRepository.findAll(org.mockito.ArgumentMatchers.any(org.springframework.data.domain.Sort.class))).thenReturn(List.of(p));
        assertTrue(service.listarTodos(LocalDate.of(2026, 6, 1), null, null, null).isEmpty());
        assertEquals(1, service.listarTodos(null, LocalDate.of(2026, 12, 31), null, null).size());
    }

    @Test
    void deletar() {
        service.deletar(5L);
        verify(pagamentoRepository).deleteById(5L);
    }
}

