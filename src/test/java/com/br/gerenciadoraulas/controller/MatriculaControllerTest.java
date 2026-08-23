package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.MatriculaDTO;
import com.br.gerenciadoraulas.service.MatriculaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MatriculaControllerTest {

    @Mock
    private MatriculaService matriculaService;

    @InjectMocks
    private MatriculaController matriculaController;

    @Test
    void consultarMatriculasPorAulaDeveRetornarIdDaPresencaQuandoPresente() {
        Long aulaId = 1L;
        MatriculaDTO dto = new MatriculaDTO(
                10L,
                LocalDate.now(),
                true,
                5L,
                "João Silva",
                2L,
                "Programa A",
                true,
                100L
        );

        when(matriculaService.consultarMatriculasPorAula(aulaId)).thenReturn(List.of(dto));

        List<MatriculaDTO> resultado = matriculaController.consultarMatriculasPorAula(aulaId);

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals(true, resultado.getFirst().getPresente());
        assertEquals(100L, resultado.getFirst().getPresencaId());
    }
}
