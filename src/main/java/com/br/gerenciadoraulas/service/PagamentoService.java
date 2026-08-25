package com.br.gerenciadoraulas.service;

import com.br.gerenciadoraulas.dto.CadastroPagamentoDTO;
import com.br.gerenciadoraulas.dto.EditarPagamentoDTO;
import com.br.gerenciadoraulas.dto.PagamentoDTO;
import com.br.gerenciadoraulas.model.Matricula;
import com.br.gerenciadoraulas.model.Pagamento;
import com.br.gerenciadoraulas.model.PagamentoMatricula;
import com.br.gerenciadoraulas.repository.MatriculaRepository;
import com.br.gerenciadoraulas.repository.PagamentoMatriculaRepository;
import com.br.gerenciadoraulas.repository.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class PagamentoService {

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private PagamentoMatriculaRepository pagamentoMatriculaRepository;

    @Autowired
    private MatriculaRepository matriculaRepository;

    public PagamentoDTO salvar(CadastroPagamentoDTO dto) {
        Pagamento pagamento = new Pagamento();
        pagamento.setValor(dto.getValor());
        pagamento.setData(dto.getData());
        pagamento = pagamentoRepository.save(pagamento);

        if (dto.getMatriculaIds() != null) {
            for (Long matriculaId : dto.getMatriculaIds()) {
                Optional<Matricula> matriculaOpt = matriculaRepository.findById(matriculaId);
                if (matriculaOpt.isPresent()) {
                    Matricula matricula = matriculaOpt.get();
                    PagamentoMatricula pm = new PagamentoMatricula();
                    pm.setPagamento(pagamento);
                    pm.setMatricula(matricula);
                    // Gravando o valor atual da mensalidade da matrícula no campo valorMensalidadeDia
                    pm.setValorMensalidadeDia(matricula.getValorMensalidade());
                    pagamentoMatriculaRepository.save(pm);
                    pagamento.getPagamentoMatriculas().add(pm);
                }
            }
        }

        return new PagamentoDTO(pagamento);
    }

    public Optional<PagamentoDTO> atualizar(Long id, EditarPagamentoDTO dto) {
        return pagamentoRepository.findById(id).map(pagamento -> {
            pagamento.setValor(dto.getValor());
            pagamento.setData(dto.getData());
            Pagamento saved = pagamentoRepository.save(pagamento);
            return new PagamentoDTO(saved);
        });
    }

    @Transactional(readOnly = true)
    public List<PagamentoDTO> listarPorAluno(Long alunoId) {
        return pagamentoRepository.findByAlunoId(alunoId).stream()
                .map(PagamentoDTO::new)
                .collect(Collectors.toList());
    }

    public void deletar(Long id) {
        pagamentoRepository.deleteById(id);
    }
}
