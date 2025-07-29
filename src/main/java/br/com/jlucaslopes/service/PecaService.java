package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.Peca;
import br.com.jlucaslopes.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PecaService {
    private final PecaRepository pecaRepository;

    @Autowired
    public PecaService(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public Peca salvar(Peca peca) {
        return pecaRepository.save(peca);
    }

    public Peca atualizaEstoque(Long id, int quantidade) {
        return pecaRepository.findById(id)
                .map(peca -> {
                    peca.setQuantidadeEstoque(peca.getQuantidadeEstoque() + quantidade);
                    return pecaRepository.save(peca);
                })
                .orElseThrow(() -> new RuntimeException("Peça não cadastrada"));
    }

    public Peca buscarPorId(Long id) {
        return pecaRepository.findById(id).orElseThrow(() -> new RuntimeException("Peça não cadastrada"));
    }

    public Peca atualizar(Long id, Peca pecaAtualizada) {
        return pecaRepository.findById(id)
                .map(peca -> {
                    peca.setDescricao(pecaAtualizada.getDescricao());
                    // outros campos...
                    return pecaRepository.save(peca);
                })
                .orElseThrow(() -> new RuntimeException("Peça não cadastrada"));
    }

    public void deletar(Long id) {
        pecaRepository.deleteById(id);
    }


}
