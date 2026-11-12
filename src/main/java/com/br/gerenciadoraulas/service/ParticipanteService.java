package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Participante;
import com.br.gerenciadoraulas.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ParticipanteService {

    @Autowired
    private ParticipanteRepository participanteRepository;

    public List<Participante> listarPorEvento(Long eventoId) {
        return participanteRepository.findByEventoId(eventoId);
    }

    public Participante salvar(Participante participante) {
        return participanteRepository.save(participante);
    }

    public void deletar(Long id) {
        participanteRepository.deleteById(id);
    }
}