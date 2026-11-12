package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.EventoDia;
import com.br.gerenciadoraulas.repository.EventoDiaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventoDiaService {

    @Autowired
    private EventoDiaRepository eventoDiaRepository;

    public List<EventoDia> listarPorEvento(Long eventoId) {
        return eventoDiaRepository.findByEventoId(eventoId);
    }

    public EventoDia salvar(EventoDia eventoDia) {
        return eventoDiaRepository.save(eventoDia);
    }

    public void deletar(Long id) {
        eventoDiaRepository.deleteById(id);
    }
}