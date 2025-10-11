package br.com.jlucaslopes.config;

import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.application.usecases.ordemservico.*;
import br.com.jlucaslopes.infrastructure.gateways.ordemservico.OrdemServiceImpl;
import br.com.jlucaslopes.infrastructure.gateways.peca.PecaServiceImpl;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteRepository;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.OrdemServicoRepository;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaRepository;
import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoRepository;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrdemConfig {

    @Bean
    public AdicionarServicoUseCase adicionarServicoUseCase(OrdemServicoGateway gateway) {
        return new AdicionarServicoUseCase(gateway);
    }

    @Bean
    public AvancarStatusUseCase avancarStatusUseCase(OrdemServicoGateway gateway) {
        return new AvancarStatusUseCase(gateway);
    }

    @Bean
    public BuscaOrdemPorIdUseCase buscaOrdemPorIdUseCase(OrdemServicoGateway gateway) {
        return new BuscaOrdemPorIdUseCase(gateway);
    }

    @Bean
    public CancelarOrdemUseCase cancelarOrdemUseCase(OrdemServicoGateway gateway) {
        return new CancelarOrdemUseCase(gateway);
    }

    @Bean
    public CriaOrdemServicoUseCase criaOrdemServicoUseCase(OrdemServicoGateway gateway) {
        return new CriaOrdemServicoUseCase(gateway);
    }

    @Bean
    public RetornarStatusUseCase retornarStatusUseCase(OrdemServicoGateway gateway) {
        return new RetornarStatusUseCase(gateway);
    }

    @Bean
    public RetornaTempoMedioUseCase retornaTempoMedioUseCase(OrdemServicoGateway gateway) {
        return new RetornaTempoMedioUseCase(gateway);
    }

    @Bean
    public ConsultarStatusUseCase consultarStatusUseCase(OrdemServicoGateway gateway) {
        return new ConsultarStatusUseCase(gateway);
    }

    @Bean
    public AprovarOrcamentoUseCase aprovarOrcamentoUseCase(OrdemServicoGateway gateway) {
        return new AprovarOrcamentoUseCase(gateway);
    }

    @Bean
    public ListarOrdensUseCase listarOrdensUseCase(OrdemServicoGateway gateway) {
        return new ListarOrdensUseCase(gateway);
    }

    @Bean
    public OrdemServicoGateway ordemServicoGateway(
            ClienteRepository clienteRepository,
            VeiculoRepository veiculoRepository,
            OrdemServicoRepository ordemServicoRepository,
            ServicoRepository servicoRepository,
            PecaRepository pecaRepository
    ) {
        PecaServiceImpl pecaService = new PecaServiceImpl(pecaRepository);

        return new OrdemServiceImpl(
                clienteRepository,
                veiculoRepository,
                ordemServicoRepository,
                servicoRepository,
                pecaRepository,
                pecaService
        );
    }
}
