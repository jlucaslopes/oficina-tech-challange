package br.com.jlucaslopes.application.gateways;

import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.domain.request.ServicoCreateRequest;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.Status;

public interface OrdemServicoGateway {

    OrdemServico criarOrdemServico(OrdemServicoCreateRequest request);

    OrdemServico buscarOrdemPorId(Long id);

    OrdemServico adicionarServico(Long ordemId, ServicoCreateRequest servicoRequest) ;

    OrdemServico avancarStatus(Long ordemId);

    OrdemServico retornarStatus(Long ordemId);

    String retornaTempoMedioDeOrdemServico();

    OrdemServico cancelarOrdem(Long id) ;

    String consultarStatus(Long id);

}
