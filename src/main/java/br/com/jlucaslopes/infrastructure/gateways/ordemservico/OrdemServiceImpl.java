package br.com.jlucaslopes.infrastructure.gateways.ordemservico;

import br.com.jlucaslopes.application.exceptions.*;
import br.com.jlucaslopes.application.gateways.OrdemServicoGateway;
import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.entities.Peca;
import br.com.jlucaslopes.domain.entities.Servico;
import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.domain.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.domain.request.ServicoCreateRequest;
import br.com.jlucaslopes.infrastructure.gateways.peca.PecaMapper;
import br.com.jlucaslopes.application.gateways.PecaGateway;
import br.com.jlucaslopes.infrastructure.gateways.servico.ServicoMapper;
import br.com.jlucaslopes.infrastructure.gateways.veiculo.VeiculoMapper;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteRepository;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.OrdemServicoEntity;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.OrdemServicoRepository;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.Status;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaRepository;
import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoEntity;
import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoRepository;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoRepository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.OptionalDouble;

public class OrdemServiceImpl implements OrdemServicoGateway{

    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final OrdemServicoRepository ordemServicoRepository;
    private final ServicoRepository servicoRepository;
    private final PecaRepository pecaRepository;
    private final PecaGateway pecaServiceImpl;


    public OrdemServiceImpl(ClienteRepository clienteRepository,
                            VeiculoRepository veiculoRepository,
                            OrdemServicoRepository ordemServicoRepository,
                            ServicoRepository servicoRepository,
                            PecaRepository pecaRepository,
                            PecaGateway pecaServiceImpl) {
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.ordemServicoRepository = ordemServicoRepository;
        this.servicoRepository = servicoRepository;
        this.pecaRepository = pecaRepository;
        this.pecaServiceImpl = pecaServiceImpl;
    }

    @Override
    public OrdemServico criarOrdemServico(OrdemServicoCreateRequest request) {

        clienteRepository.findClienteByDocumento(request.getIdCliente())
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado com o documento: " + request.getIdCliente()));

        Veiculo veiculo = veiculoRepository.findByPlaca(request.getPlacaVeiculo())
                .map(VeiculoMapper::toVeiculo)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veiculo não encontrado com a placa: " + request.getPlacaVeiculo()));

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setVeiculo(veiculo);
        ordemServico.setDescricao(request.getDescricao());

        OrdemServicoEntity ordemServicoEntity = ordemServicoRepository.saveAndFlush(OrdemServicoMapper.toEntity(ordemServico));

        return OrdemServicoMapper.toOrdemServico(ordemServicoEntity);
    }

    @Override
    public OrdemServico buscarOrdemPorId(Long id) {
        return ordemServicoRepository.findById(id)
                .map(OrdemServicoMapper::toOrdemServico)
                .orElseThrow(() -> new OrdemNaoEncontradaException("Ordem de serviço não encontrada com o ID: " + id));
    }

    @Override
    public OrdemServico adicionarServico(Long ordemId, ServicoCreateRequest servicoRequest) {
        OrdemServico ordemServico = buscarOrdemPorId(ordemId);

        if(ordemServico.getStatus() != Status.EM_DIAGNOSTICO ) {
            throw new OrdemStatusInvalidoAddServicoException("Nao é possível adicionar serviços a uma ordem que não esteja em diagnóstico");
        }

        Servico servico;
        Peca peca = null;
        if(servicoRequest.getIdPeca() != null) {
            peca = pecaRepository.findById(servicoRequest.getIdPeca())
                    .map(PecaMapper::toPeca)
                    .orElseThrow(() -> new PecaNaoEncontradaException("Peça não encontrada: " + servicoRequest.getIdPeca()));
            if(peca.getQuantidadeEstoque() < servicoRequest.getQuantidade())
                throw new EstoqueInsuficienteException("Estoque insuficiente para a peça: " + peca.getDescricao());

            pecaServiceImpl.atualizaEstoque(peca.getId(), Math.toIntExact(servicoRequest.getQuantidade() * -1));

            servico = servicoRequest.toServico(peca);

        } else {
            servico = servicoRequest.toServico();
        }
        servico.setOrdemServico(ordemServico);
        ServicoEntity servicoEntity = ServicoMapper.toEntity(servico);

        Servico servicoSalvo = ServicoMapper.toServico(servicoRepository.save(servicoEntity));

        ordemServico.getServicos().add(servicoSalvo);

        OrdemServicoEntity entity = ordemServicoRepository.saveAndFlush(OrdemServicoMapper.toEntity(ordemServico));
        return OrdemServicoMapper.toOrdemServico(entity);
    }

    @Override
    public OrdemServico avancarStatus(Long ordemId) {
        OrdemServico ordemServico = buscarOrdemPorId(ordemId);
        ordemServico.setStatus(ordemServico.getStatus().avancar());
        if (ordemServico.getStatus() == Status.ENTREGUE) {
            ordemServico.setDataFim(OffsetDateTime.now());
        }
        OrdemServicoEntity ordemSaved = ordemServicoRepository.saveAndFlush(OrdemServicoMapper.toEntity(ordemServico));
        return OrdemServicoMapper.toOrdemServico(ordemSaved);
    }

    @Override
    public OrdemServico retornarStatus(Long ordemId) {
        OrdemServico ordemServico = buscarOrdemPorId(ordemId);
        ordemServico.setStatus(ordemServico.getStatus().retornar());
        OrdemServicoEntity ordemSaved = ordemServicoRepository.saveAndFlush(OrdemServicoMapper.toEntity(ordemServico));
        return OrdemServicoMapper.toOrdemServico(ordemSaved);
    }

    @Override
    public OrdemServico cancelarOrdem(Long id) {
        OrdemServico ordemServico = buscarOrdemPorId(id);
        if (ordemServico.getStatus() == Status.ENTREGUE || ordemServico.getStatus() == Status.CANCELADA || ordemServico.getStatus() == Status.FINALIZADA) {
            throw new CancelarOrdemException("Ordem nao pode ser cancelada");
        }
        ordemServico.setStatus(Status.CANCELADA);
        ordemServico.setDataFim(OffsetDateTime.now());
        OrdemServicoEntity ordemSaved = ordemServicoRepository.saveAndFlush(OrdemServicoMapper.toEntity(ordemServico));
        return OrdemServicoMapper.toOrdemServico(ordemSaved);
    }

    @Override
    public String retornaTempoMedioDeOrdemServico() {

        OptionalDouble average = ordemServicoRepository.findAllByStatus(Status.ENTREGUE)
                .stream()
                .mapToLong(ordem -> ordem.getDataFim().toEpochSecond() - ordem.getDataInicio().toEpochSecond())
                .average();

        if (average.isPresent()) {
            long averageSeconds = (long) average.getAsDouble();
            long hours = averageSeconds / 3600;
            long minutes = (averageSeconds % 3600) / 60;
            long seconds = averageSeconds % 60;

            return String.format("Tempo médio de ordem de serviço: %02d:%02d:%02d", hours, minutes, seconds);
        }

        return "Nenhuma ordem de serviço finalizada foi encontrada.";
    }

    @Override
    public String consultarStatus(Long id) {
        return ordemServicoRepository.findById(id)
                .map(OrdemServicoEntity::getStatus)
                .map(Status::toString)
                .orElseThrow(() -> new OrdemNaoEncontradaException("Ordem de serviço não encontrada com o ID: " + id));
    }

    @Override
    public void aprovaOrcamento(Long id, boolean aprovado) {
        OrdemServico ordemServico = buscarOrdemPorId(id);
        if (ordemServico.getStatus() != Status.AGUARDANDO_APROVACAO) {
            throw new OrdemStatusInvalidoAprovaOrcamentoException("Ordem de serviço não está aguardando aprovação de orçamento.");
        }
        if (aprovado) {
            ordemServico.setStatus(Status.EM_EXECUCAO);
        } else {
            ordemServico.setStatus(Status.CANCELADA);
            ordemServico.setDataFim(OffsetDateTime.now());
        }
        ordemServicoRepository.saveAndFlush(OrdemServicoMapper.toEntity(ordemServico));
    }

    @Override
    public List<OrdemServico> listarOrdemServicos() {
        return ordemServicoRepository.findAllForListagem(List.of(Status.FINALIZADA, Status.ENTREGUE))
                .stream()
                .map(OrdemServicoMapper::toOrdemServico)
                .toList();
    }

}
