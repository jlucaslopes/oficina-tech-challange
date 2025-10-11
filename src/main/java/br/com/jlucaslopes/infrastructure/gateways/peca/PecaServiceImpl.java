package br.com.jlucaslopes.infrastructure.gateways.peca;

import br.com.jlucaslopes.application.exceptions.PecaNaoEncontradaException;
import br.com.jlucaslopes.application.exceptions.QuantidadeInvalidaEstoque;
import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.domain.entities.Peca;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaEntity;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaRepository;

public class PecaServiceImpl implements PecaGateway {

    public static final String PECA_NAO_CADASTRADA_MESSAGE = "Peça não cadastrada";
    private final PecaRepository pecaRepository;

    public PecaServiceImpl(PecaRepository pecaRepository) {
        this.pecaRepository = pecaRepository;
    }

    public Peca salvar(Peca peca) {
        PecaEntity entity = PecaMapper.toEntity(peca);
        PecaEntity saved = pecaRepository.save(entity);
        return PecaMapper.toPeca(saved);
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
                .map(PecaMapper::toPeca)
                .orElseThrow(() -> new PecaNaoEncontradaException(PECA_NAO_CADASTRADA_MESSAGE));
    }

    public Peca buscarPorId(Long id) {
        return pecaRepository.findById(id)
                .map(PecaMapper::toPeca)
                .orElseThrow(() -> new PecaNaoEncontradaException(PECA_NAO_CADASTRADA_MESSAGE));
    }

    public Peca atualizar(Long id, Peca pecaAtualizada) {
        return pecaRepository.findById(id)
                .map(peca -> {
                    peca.setDescricao(pecaAtualizada.getDescricao());
                    peca.setValorUnitario(pecaAtualizada.getValorUnitario());
                    peca.setQuantidadeEstoque(pecaAtualizada.getQuantidadeEstoque());
                    return pecaRepository.save(peca);
                })
                .map(PecaMapper::toPeca)
                .orElseThrow(() -> new PecaNaoEncontradaException(PECA_NAO_CADASTRADA_MESSAGE));
    }

    public void deletar(Long id) {
        pecaRepository.deleteById(id);
    }


}
