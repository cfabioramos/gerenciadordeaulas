-- ============================================================================
-- SEED: Dados de exemplo para o módulo Eventos e Viagens
-- Execute este script APÓS o DDL principal (sql/ddl.sql.txt)
-- ============================================================================

-- ==============================
-- 1. Eventos
-- ==============================

-- Evento: Workshop de Forró 2026
INSERT INTO evento (titulo, descricao, tipo, local, status, participantes_qtd)
VALUES ('Workshop de Forró 2026', 'Oficina intensiva de passos básicos e intermediários de forró', 'EVENTO', 'Salão de Dança Ziriguidum', 'AGENDADO', 2)
RETURNING id INTO @evento1_id;

-- Viagem: Salvador
INSERT INTO evento (titulo, descricao, tipo, local, status, participantes_qtd)
VALUES ('Viagem Cultural - Salvador', 'Viagem de estudos sobre cultura popular baiana', 'VIAGEM', 'Salvador, BA', 'CONFIRMADO', 2)
RETURNING id INTO @evento2_id;

-- Evento cancelado
INSERT INTO evento (titulo, descricao, tipo, local, status, participantes_qtd)
VALUES ('Encontro de Dança Circular', 'Rodas de dança circular com músicas regionais', 'EVENTO', 'Centro Cultural', 'CANCELADO', 0)
RETURNING id INTO @evento3_id;

-- ==============================
-- 2. Dias do Evento (apenas para tipo EVENTO)
-- ==============================

-- Dias do Workshop de Forró
INSERT INTO evento_dia (evento_id, data_evento, horario)
VALUES
  (@evento1_id, '2026-09-10', '19:00'),
  (@evento1_id, '2026-09-17', '19:00'),
  (@evento1_id, '2026-09-24', '19:00'),
  (@evento1_id, '2026-10-01', '19:00');

-- Dia do Encontro de Dança Circular
INSERT INTO evento_dia (evento_id, data_evento, horario)
VALUES
  (@evento3_id, '2026-10-05', '14:00');

-- ==============================
-- 3. Participantes
-- ==============================

-- Workshop de Forró
INSERT INTO participante (nome, evento_id)
VALUES ('Carlos Fábio Ramos', @evento1_id),
       ('João Visitante', @evento1_id);

-- Viagem Cultural
INSERT INTO participante (nome, evento_id)
VALUES ('Marcos Afonso', @evento2_id),
       ('Ana Paula Silva', @evento2_id);

-- ==============================
-- 4. Receitas
-- ==============================

INSERT INTO receita (descricao, valor, evento_id, participante_id, data_lancamento)
VALUES
  ('Inscrição workshop', 150.00, @evento1_id, (SELECT id FROM participante WHERE nome = 'Carlos Fábio Ramos' AND evento_id = @evento1_id LIMIT 1), '2026-09-01 10:00:00'),
  ('Inscrição workshop', 100.00, @evento1_id, (SELECT id FROM participante WHERE nome = 'João Visitante' AND evento_id = @evento1_id LIMIT 1), '2026-09-02 11:00:00'),
  ('Pacote viagem', 1200.00, @evento2_id, (SELECT id FROM participante WHERE nome = 'Marcos Afonso' AND evento_id = @evento2_id LIMIT 1), '2026-08-20 09:00:00'),
  ('Pacote viagem', 1200.00, @evento2_id, (SELECT id FROM participante WHERE nome = 'Ana Paula Silva' AND evento_id = @evento2_id LIMIT 1), '2026-08-21 09:00:00');

-- ==============================
-- 5. Despesas
-- ==============================

INSERT INTO despesa (descricao, valor, evento_id, data_lancamento)
VALUES
  ('Aluguel do salão', 200.00, @evento1_id, '2026-09-10 18:00:00'),
  ('Coffee break', 80.00, @evento1_id, '2026-09-10 20:00:00'),
  ('Passagem aérea', 800.00, @evento2_id, '2026-10-15 08:00:00'),
  ('Hospedagem', 600.00, @evento2_id, '2026-10-15 09:00:00'),
  ('Transporte local', 150.00, @evento2_id, '2026-10-16 10:00:00');

-- ============================================================================
-- FIM DO SEED
-- ============================================================================