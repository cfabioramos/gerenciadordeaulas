package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.TipoEvento;
import com.br.gerenciadoraulas.repository.EventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarTodos() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> buscarPorId(Long id) {
        return eventoRepository.findById(id);
    }

    public List<Evento> buscarPorTipo(TipoEvento tipo) {
        return eventoRepository.findByTipo(tipo);
    }

    @Transactional
    public Evento salvar(Evento evento) {
        return eventoRepository.save(evento);
    }

    @Transactional
    public void deletar(Long id) {
        eventoRepository.deleteById(id);
    }
}