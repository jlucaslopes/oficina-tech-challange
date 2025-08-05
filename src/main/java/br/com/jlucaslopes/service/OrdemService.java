package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.OrdemServico;
import br.com.jlucaslopes.model.request.OrdemServicoCreateRequest;
import br.com.jlucaslopes.repository.ClienteRepository;
import br.com.jlucaslopes.repository.OrdemServicoRepository;
import br.com.jlucaslopes.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrdemService {

    private final OrdemServicoRepository ordemRepository;
    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;

    @Autowired
    public OrdemService(OrdemServicoRepository ordemRepository,
                        ClienteRepository clienteRepository,
                        VeiculoRepository veiculoRepository) {
        this.ordemRepository = ordemRepository;
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public OrdemServico criarOrdemServico(OrdemServicoCreateRequest request) {

        clienteRepository.findClienteByDocumento(request.getIdCliente())
                .orElseThrow(() -> new IllegalArgumentException("Cliente não encontrado com o documento: " + request.getIdCliente()));

        veiculoRepository.findByPlaca(request.getPlacaVeiculo())
                .orElseThrow(() -> new IllegalArgumentException("Veiculo não encontrado com a placa: " + request.getPlacaVeiculo()));

        OrdemServico ordemServico = new OrdemServico();
        ordemServico.setVeiculo();

        return null;
    }
}
