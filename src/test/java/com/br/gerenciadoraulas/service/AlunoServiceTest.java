package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.AlunoDTO;
import com.br.gerenciadoraulas.model.Aluno;
import com.br.gerenciadoraulas.repository.AlunoRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AlunoServiceTest {

    @Mock
    private AlunoRepository alunoRepository;

    @InjectMocks
    private AlunoService service;

    @Test
    void listarTodosMapeiaParaDTO() {
        Aluno a = new Aluno();
        a.setId(1L);
        a.setNome("Ana");
        when(alunoRepository.findAll()).thenReturn(List.of(a));

        List<AlunoDTO> result = service.listarTodos();
        assertEquals(1, result.size());
        assertEquals("Ana", result.get(0).getNome());
    }

    @Test
    void listarNaoMatriculadosDelegaAoRepositorio() {
        when(alunoRepository.findAlunosNaoMatriculados(5L)).thenReturn(List.of());
        assertEquals(0, service.listarNaoMatriculados(5L).size());
        verify(alunoRepository).findAlunosNaoMatriculados(5L);
    }

    @Test
    void salvarPersisteERetornaDTO() {
        Aluno a = new Aluno();
        a.setNome("Bia");
        Aluno saved = new Aluno();
        saved.setId(2L);
        saved.setNome("Bia");
        when(alunoRepository.save(a)).thenReturn(saved);

        AlunoDTO dto = service.salvar(a);
        assertEquals(2L, dto.getId());
        verify(alunoRepository).save(a);
    }

    @Test
    void buscarPorIdExistenteRetornaDTO() {
        Aluno a = new Aluno();
        a.setId(3L);
        a.setNome("Caio");
        when(alunoRepository.findById(3L)).thenReturn(Optional.of(a));

        AlunoDTO dto = service.buscarPorId(3L);
        assertEquals("Caio", dto.getNome());
    }

    @Test
    void buscarPorIdInexistenteLanca() {
        when(alunoRepository.findById(99L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> service.buscarPorId(99L));
    }
}
