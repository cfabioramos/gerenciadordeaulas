package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Receita;
import com.br.gerenciadoraulas.repository.ReceitaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReceitaService {

    @Autowired
    private ReceitaRepository receitaRepository;

    public List<Receita> listarPorEvento(Long eventoId) {
        return receitaRepository.findByEventoId(eventoId);
    }

    public Receita salvar(Receita receita) {
        return receitaRepository.save(receita);
    }

    public void deletar(Long id) {
        receitaRepository.deleteById(id);
    }
}