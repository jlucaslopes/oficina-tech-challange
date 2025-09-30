package br.com.jlucaslopes.naousar.service;

import br.com.jlucaslopes.naousar.model.Peca;
import br.com.jlucaslopes.application.exceptions.PecaNaoEncontradaException;
import br.com.jlucaslopes.application.exceptions.QuantidadeInvalidaEstoque;
import br.com.jlucaslopes.naousar.repository.PecaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PecaService {
    public static final String PECA_NAO_CADASTRADA_MESSAGE = "Peça não cadastrada";
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
                    if(peca.getQuantidadeEstoque() + quantidade < 0) {
                        throw new QuantidadeInvalidaEstoque("Quantidade inválida para atualização de estoque");
                    }
                    peca.setQuantidadeEstoque(peca.getQuantidadeEstoque() + quantidade);
                    return pecaRepository.save(peca);
                })
                .orElseThrow(() -> new PecaNaoEncontradaException(PECA_NAO_CADASTRADA_MESSAGE));
    }

    public Peca buscarPorId(Long id) {
        return pecaRepository.findById(id).orElseThrow(() -> new PecaNaoEncontradaException(PECA_NAO_CADASTRADA_MESSAGE));
    }

    public Peca atualizar(Long id, Peca pecaAtualizada) {
        return pecaRepository.findById(id)
                .map(peca -> {
                    peca.setDescricao(pecaAtualizada.getDescricao());
                    peca.setValorUnitario(pecaAtualizada.getValorUnitario());
                    peca.setQuantidadeEstoque(pecaAtualizada.getQuantidadeEstoque());
                    return pecaRepository.save(peca);
                })
                .orElseThrow(() -> new PecaNaoEncontradaException(PECA_NAO_CADASTRADA_MESSAGE));
    }

    public void deletar(Long id) {
        pecaRepository.deleteById(id);
    }


}
