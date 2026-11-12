package com.br.gerenciadoraulas.config;

import com.br.gerenciadoraulas.model.*;
import com.br.gerenciadoraulas.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Profile("!test")
public class DataLoader {

    @Bean
    CommandLineRunner eventoDiaLoader(
            EventoRepository eventoRepository,
            EventoDiaRepository eventoDiaRepository,
            ParticipanteRepository participanteRepository,
            ReceitaRepository receitaRepository,
            DespesaRepository despesaRepository) {

        return args -> {
            if (eventoRepository.count() > 0) {
                return; // já possui dados
            }

            // ==============================
            // 1. Eventos
            // ==============================
            Evento evento1 = new Evento();
            evento1.setTitulo("Workshop de Forró 2026");
            evento1.setDescricao("Oficina intensiva de passos básicos e intermediários de forró");
            evento1.setTipo(TipoEvento.EVENTO);
            evento1.setLocal("Salão de Dança Ziriguidum");
            evento1.setStatus("AGENDADO");
            evento1.setParticipantesQtd(2);
            evento1.setDataInicio(LocalDateTime.of(2026, 9, 10, 19, 0));
            evento1.setDataFim(LocalDateTime.of(2026, 10, 1, 19, 0));
            evento1.setHorarioInicio("19:00");
            evento1.setHorarioFim("22:00");
            evento1 = eventoRepository.save(evento1);

            // Dias do Workshop (4 datas)
            List<EventoDia> dias1 = new ArrayList<>();
            String[][] datasHorarios1 = {
                    {"2026-09-10", "19:00"},
                    {"2026-09-17", "19:00"},
                    {"2026-09-24", "19:00"},
                    {"2026-10-01", "19:00"}
            };
            for (String[] dh : datasHorarios1) {
                EventoDia d = new EventoDia();
                d.setEvento(evento1);
                d.setDataEvento(LocalDate.parse(dh[0]));
                d.setHorario(dh[1]);
                dias1.add(d);
            }
            eventoDiaRepository.saveAll(dias1);
            evento1.setDias(new java.util.LinkedHashSet<>(dias1));

            // ==============================
            // 2. Viagem
            // ==============================
            Evento evento2 = new Evento();
            evento2.setTitulo("Viagem Cultural - Salvador");
            evento2.setDescricao("Viagem de estudos sobre cultura popular baiana");
            evento2.setTipo(TipoEvento.VIAGEM);
            evento2.setLocal("Salvador, BA");
            evento2.setStatus("CONFIRMADO");
            evento2.setParticipantesQtd(2);
            evento2.setDataInicio(LocalDateTime.of(2026, 10, 15, 8, 0));
            evento2.setDataFim(LocalDateTime.of(2026, 10, 20, 22, 0));
            evento2.setHorarioInicio("08:00");
            evento2.setHorarioFim("22:00");
            evento2 = eventoRepository.save(evento2);

            // ==============================
            // 3. Evento cancelado
            // ==============================
            Evento evento3 = new Evento();
            evento3.setTitulo("Encontro de Dança Circular");
            evento3.setDescricao("Rodas de dança circular com músicas regionais");
            evento3.setTipo(TipoEvento.EVENTO);
            evento3.setLocal("Centro Cultural");
            evento3.setStatus("CANCELADO");
            evento3.setParticipantesQtd(0);
            evento3.setDataInicio(LocalDateTime.of(2026, 10, 5, 14, 0));
            evento3.setDataFim(LocalDateTime.of(2026, 10, 5, 17, 0));
            evento3.setHorarioInicio("14:00");
            evento3.setHorarioFim("17:00");
            evento3 = eventoRepository.save(evento3);

            List<EventoDia> dias3 = new ArrayList<>();
            EventoDia d3 = new EventoDia();
            d3.setEvento(evento3);
            d3.setDataEvento(LocalDate.parse("2026-10-05"));
            d3.setHorario("14:00");
            dias3.add(d3);
            eventoDiaRepository.saveAll(dias3);
            evento3.setDias(new java.util.LinkedHashSet<>(dias3));

            // ==============================
            // 4. Participantes
            // ==============================
            Participante p1 = new Participante();
            p1.setNome("Carlos Fábio Ramos");
            p1.setEvento(evento1);
            p1 = participanteRepository.save(p1);

            Participante p2 = new Participante();
            p2.setNome("João Visitante");
            p2.setEvento(evento1);
            p2 = participanteRepository.save(p2);

            Participante p3 = new Participante();
            p3.setNome("Marcos Afonso");
            p3.setEvento(evento2);
            p3 = participanteRepository.save(p3);

            Participante p4 = new Participante();
            p4.setNome("Ana Paula Silva");
            p4.setEvento(evento2);
            p4 = participanteRepository.save(p4);

            // ==============================
            // 5. Receitas
            // ==============================
            Receita r1 = new Receita();
            r1.setDescricao("Inscrição workshop");
            r1.setValor(new BigDecimal("150.00"));
            r1.setEvento(evento1);
            r1.setParticipante(p1);
            r1.setDataLancamento(LocalDateTime.of(2026, 9, 1, 10, 0));
            receitaRepository.save(r1);

            Receita r2 = new Receita();
            r2.setDescricao("Inscrição workshop");
            r2.setValor(new BigDecimal("100.00"));
            r2.setEvento(evento1);
            r2.setParticipante(p2);
            r2.setDataLancamento(LocalDateTime.of(2026, 9, 2, 11, 0));
            receitaRepository.save(r2);

            Receita r3 = new Receita();
            r3.setDescricao("Pacote viagem");
            r3.setValor(new BigDecimal("1200.00"));
            r3.setEvento(evento2);
            r3.setParticipante(p3);
            r3.setDataLancamento(LocalDateTime.of(2026, 8, 20, 9, 0));
            receitaRepository.save(r3);

            Receita r4 = new Receita();
            r4.setDescricao("Pacote viagem");
            r4.setValor(new BigDecimal("1200.00"));
            r4.setEvento(evento2);
            r4.setParticipante(p4);
            r4.setDataLancamento(LocalDateTime.of(2026, 8, 21, 9, 0));
            receitaRepository.save(r4);

            // ==============================
            // 6. Despesas
            // ==============================
            Despesa de1 = new Despesa();
            de1.setDescricao("Aluguel do salão");
            de1.setValor(new BigDecimal("200.00"));
            de1.setEvento(evento1);
            de1.setDataLancamento(LocalDateTime.of(2026, 9, 10, 18, 0));
            despesaRepository.save(de1);

            Despesa de2 = new Despesa();
            de2.setDescricao("Coffee break");
            de2.setValor(new BigDecimal("80.00"));
            de2.setEvento(evento1);
            de2.setDataLancamento(LocalDateTime.of(2026, 9, 10, 20, 0));
            despesaRepository.save(de2);

            Despesa de3 = new Despesa();
            de3.setDescricao("Passagem aérea");
            de3.setValor(new BigDecimal("800.00"));
            de3.setEvento(evento2);
            de3.setDataLancamento(LocalDateTime.of(2026, 10, 15, 8, 0));
            despesaRepository.save(de3);

            Despesa de4 = new Despesa();
            de4.setDescricao("Hospedagem");
            de4.setValor(new BigDecimal("600.00"));
            de4.setEvento(evento2);
            de4.setDataLancamento(LocalDateTime.of(2026, 10, 15, 9, 0));
            despesaRepository.save(de4);

            Despesa de5 = new Despesa();
            de5.setDescricao("Transporte local");
            de5.setValor(new BigDecimal("150.00"));
            de5.setEvento(evento2);
            de5.setDataLancamento(LocalDateTime.of(2026, 10, 16, 10, 0));
            despesaRepository.save(de5);

            System.out.println(">>> DataLoader: dados de Eventos e Viagens populados com sucesso.");
        };
    }
}