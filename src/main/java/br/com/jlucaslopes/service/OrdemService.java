package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.*;
import br.com.jlucaslopes.model.exception.CancelarOrdemException;
import br.com.jlucaslopes.model.exception.EstoqueInsuficienteException;
import br.com.jlucaslopes.model.exception.OrdemStatusInvalidoAddServicoException;
import br.com.jlucaslopes.model.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.model.request.ServicoCreateRequest;
import br.com.jlucaslopes.repository.ClienteRepository;
import br.com.jlucaslopes.repository.OrdemServicoRepository;
import br.com.jlucaslopes.repository.PecaRepository;
import br.com.jlucaslopes.repository.VeiculoRepository;
import br.com.jlucaslopes.repository.ServicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.OptionalDouble;

@Service
public class OrdemService {

    private final OrdemServicoRepository ordemRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;
    private final PecaRepository pecaRepository;
    private final PecaService pecaService;
    private final ServicoRepository servicoRepository;
    private final OrdemServicoRepository ordemServicoRepository;

    @Autowired
    public OrdemService(OrdemServicoRepository ordemRepository,
                        ClienteRepository clienteRepository,
                        VeiculoRepository veiculoRepository,
                        PecaRepository pecaRepository,
                        PecaService pecaService,
                        ServicoRepository servicoRepository, OrdemServicoRepository ordemServicoRepository) {
        this.servicoRepository = servicoRepository;
        this.ordemRepository = ordemRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
        this.pecaRepository = pecaRepository;
        this.pecaService = pecaService;
        this.ordemServicoRepository = ordemServicoRepository;
    }

    public OrdemServico criarOrdemServico(OrdemServicoCreateRequest request) {

        clienteRepository.findClienteByDocumento(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o documento: " + request.getIdCliente()));

        Veiculo veiculo = veiculoRepository.findByPlaca(request.getPlacaVeiculo())
                .orElseThrow(() -> new IllegalArgumentException("Veiculo não encontrado com a placa: " + request.getPlacaVeiculo()));

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setVeiculo(veiculo);
        ordemServico.setDescricao(request.getDescricao());

        return ordemRepository.saveAndFlush(ordemServico);
    }

    public OrdemServico buscarOrdemPorId(Long id) {
        return ordemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ordem de serviço não encontrada com o ID: " + id));
    }

    public OrdemServico adicionarServico(Long ordemId, ServicoCreateRequest servicoRequest) {
        OrdemServico ordemServico = buscarOrdemPorId(ordemId);

        if(ordemServico.getStatus() != Status.EM_DIAGNOSTICO ) {
            throw new OrdemStatusInvalidoAddServicoException("Nao é possível adicionar serviços a uma ordem que não esteja em diagnóstico");
        }

        Servico servico;
        Peca peca = null;
        if(servicoRequest.getIdPeca() != null) {
            peca = pecaRepository.findById(servicoRequest.getIdPeca())
                    .orElseThrow(() -> new IllegalArgumentException("Peça não encontrada: " + servicoRequest.getIdPeca()));
            if(peca.getQuantidadeEstoque() < servicoRequest.getQuantidade())
                throw new EstoqueInsuficienteException("Estoque insuficiente para a peça: " + peca.getDescricao());

            pecaService.atualizaEstoque(peca.getId(), Math.toIntExact(servicoRequest.getQuantidade() * -1));

            servico = servicoRequest.toServico(peca);

        } else {
            servico = servicoRequest.toServico();
        }
        servico.setOrdemServico(ordemServico);

        Servico servicoSalvo = servicoRepository.save(servico);

        ordemServico.getServicos().add(servicoSalvo);

        return ordemRepository.saveAndFlush(ordemServico);
    }

    public OrdemServico avancarStatus(Long ordemId) {
        OrdemServico ordemServico = buscarOrdemPorId(ordemId);
        ordemServico.setStatus(ordemServico.getStatus().avancar());
        if (ordemServico.getStatus() == Status.ENTREGUE) {
            ordemServico.setDataFim(OffsetDateTime.now());
        }
        return ordemRepository.saveAndFlush(ordemServico);
    }

    public OrdemServico retornarStatus(Long ordemId) {
        OrdemServico ordemServico = buscarOrdemPorId(ordemId);
        ordemServico.setStatus(ordemServico.getStatus().retornar());
        return ordemRepository.saveAndFlush(ordemServico);
    }

    public String retornaTempoMedioDeOrdemServico() {

        OptionalDouble average = ordemRepository.findAllByStatus(Status.ENTREGUE)
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

    public OrdemServico cancelarOrdem(Long id) {
        OrdemServico ordemServico = buscarOrdemPorId(id);
        if (ordemServico.getStatus() == Status.ENTREGUE || ordemServico.getStatus() == Status.CANCELADA || ordemServico.getStatus() == Status.FINALIZADA) {
            throw new CancelarOrdemException("Ordem nao pode ser cancelada");
        }
        ordemServico.setStatus(Status.CANCELADA);
        ordemServico.setDataFim(OffsetDateTime.now());
        return ordemServicoRepository.saveAndFlush(ordemServico);
    }
}
