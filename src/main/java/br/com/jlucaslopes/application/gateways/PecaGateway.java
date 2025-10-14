package br.com.jlucaslopes.application.gateways;

import br.com.jlucaslopes.domain.entities.Peca;

public interface PecaGateway {

     Peca salvar(Peca peca) ;

     Peca atualizaEstoque(Long id, int quantidade);

     Peca buscarPorId(Long id) ;

     Peca atualizar(Long id, Peca pecaAtualizada) ;

     void deletar(Long id);

}
