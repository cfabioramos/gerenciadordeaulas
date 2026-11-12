package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Despesa;
import com.br.gerenciadoraulas.repository.DespesaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DespesaService {

    @Autowired
    private DespesaRepository despesaRepository;

    public List<Despesa> listarPorEvento(Long eventoId) {
        return despesaRepository.findByEventoId(eventoId);
    }

    public Despesa salvar(Despesa despesa) {
        return despesaRepository.save(despesa);
    }

    public void deletar(Long id) {
        despesaRepository.deleteById(id);
    }
}