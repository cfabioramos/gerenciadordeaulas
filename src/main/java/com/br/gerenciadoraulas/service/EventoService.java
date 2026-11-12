package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.model.Evento;
import com.br.gerenciadoraulas.model.EventoDia;
import com.br.gerenciadoraulas.model.Participante;
import com.br.gerenciadoraulas.model.TipoEvento;
import com.br.gerenciadoraulas.repository.EventoDiaRepository;
import com.br.gerenciadoraulas.repository.EventoRepository;
import com.br.gerenciadoraulas.repository.ParticipanteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private EventoDiaRepository eventoDiaRepository;

    @Autowired
    private ParticipanteRepository participanteRepository;

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
        vincularFilhos(evento);
        return eventoRepository.save(evento);
    }

    /**
     * Atualiza os dados básicos do evento e reconcilia dias e participantes.
     *
     * Importante: as coleções LAZY do evento (dias/participantes) NÃO são tocadas
     * aqui — os filhos são gerenciados diretamente pelos repositórios. Assim o
     * flush final não precisa de merge em cascata sobre instâncias removidas
     * (evita ObjectDeletedException).
     *
     * - Dias: lista é substituída integralmente pelo payload.
     * - Participantes: os que vêm com id são mantidos; sem id são criados;
     *   os existentes ausentes do payload são removidos.
     */
    @Transactional
    public Evento atualizar(Long id, Evento dados) {
        Evento evento = eventoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Evento não encontrado"));

        // Escalares: mutação direta na instância gerenciada (flush automático no commit)
        evento.setTitulo(dados.getTitulo());
        evento.setDescricao(dados.getDescricao());
        evento.setTipo(dados.getTipo());
        evento.setLocal(dados.getLocal());
        evento.setStatus(dados.getStatus());
        evento.setDataInicio(dados.getDataInicio());
        evento.setDataFim(dados.getDataFim());
        evento.setHorarioInicio(dados.getHorarioInicio());
        evento.setHorarioFim(dados.getHorarioFim());

        // --- Participantes: reconciliação por id ou nome (via repositório) ---
        if (dados.getParticipantes() != null) {
            List<Participante> atuais = participanteRepository.findByEventoId(id);
            Set<Long> mantidos = new HashSet<>();
            List<Participante> novos = new ArrayList<>();
            for (Participante p : dados.getParticipantes()) {
                if (p.getId() != null && atuais.stream().anyMatch(a -> p.getId().equals(a.getId()))) {
                    mantidos.add(p.getId());
                    continue;
                }
                String nome = p.getNome() == null ? null : p.getNome().trim();
                if (nome == null || nome.isEmpty()) {
                    continue;
                }
                // Mantém o participante já existente com o mesmo nome (preserva
                // as referências de receita em participante_id)
                Optional<Participante> existente = atuais.stream()
                        .filter(a -> nome.equalsIgnoreCase(a.getNome().trim()))
                        .findFirst();
                if (existente.isPresent()) {
                    mantidos.add(existente.get().getId());
                } else {
                    Participante novo = new Participante();
                    novo.setNome(nome);
                    novo.setEvento(evento);
                    novos.add(novo);
                }
            }
            List<Participante> removidos = atuais.stream()
                    .filter(a -> !mantidos.contains(a.getId()))
                    .toList(); // receita.participante_id -> SET NULL no banco
            if (!removidos.isEmpty()) {
                participanteRepository.deleteAll(removidos);
            }
            if (!novos.isEmpty()) {
                participanteRepository.saveAll(novos);
            }
        }

        // --- Dias: substituição integral (via repositório) ---
        if (dados.getDias() != null) {
            eventoDiaRepository.deleteByEventoId(id);
            List<EventoDia> novos = new ArrayList<>();
            for (EventoDia d : dados.getDias()) {
                EventoDia novo = new EventoDia();
                novo.setDataEvento(d.getDataEvento());
                novo.setHorario(d.getHorario());
                novo.setEvento(evento);
                novos.add(novo);
            }
            if (!novos.isEmpty()) {
                eventoDiaRepository.saveAll(novos);
            }
        }

        evento.setParticipantesQtd(participanteRepository.findByEventoId(id).size());
        return evento; // instância gerenciada; coleções LAZY são carregadas na serialização
    }

    @Transactional
    public void deletar(Long id) {
        eventoRepository.deleteById(id);
    }

    /** O lado inverso do relacionamento (mappedBy) não é preenchido pelo JPA. */
    private void vincularFilhos(Evento evento) {
        if (evento.getDias() != null) {
            evento.getDias().forEach(d -> {
                d.setId(null);
                d.setEvento(evento);
            });
        }
        if (evento.getParticipantes() != null) {
            evento.getParticipantes().forEach(p -> {
                p.setId(null);
                p.setEvento(evento);
            });
        }
        evento.setParticipantesQtd(evento.getParticipantes() != null ? evento.getParticipantes().size() : 0);
    }
}
