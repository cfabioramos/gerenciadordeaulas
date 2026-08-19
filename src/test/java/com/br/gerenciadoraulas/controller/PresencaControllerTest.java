package com.br.gerenciadoraulas.controller;

import com.br.gerenciadoraulas.dto.PresencaDTO;
import com.br.gerenciadoraulas.service.PresencaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PresencaControllerTest {

    @Mock
    private PresencaService presencaService;

    @InjectMocks
    private PresencaController presencaController;

    @Test
    void criarDeveReceberPresencaDTOComMatriculaIdEAulaId() {
        PresencaDTO inputDto = new PresencaDTO();
        inputDto.setMatriculaId(20L);
        inputDto.setAulaId(30L);

        PresencaDTO outputDto = new PresencaDTO();
        outputDto.setId(1L);
        outputDto.setMatriculaId(20L);
        outputDto.setAulaId(30L);

        when(presencaService.salvar(any(PresencaDTO.class))).thenReturn(outputDto);

        ResponseEntity<PresencaDTO> response = presencaController.criar(inputDto);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(20L, response.getBody().getMatriculaId());
        assertEquals(30L, response.getBody().getAulaId());

        verify(presencaService).salvar(inputDto);
    }
}
