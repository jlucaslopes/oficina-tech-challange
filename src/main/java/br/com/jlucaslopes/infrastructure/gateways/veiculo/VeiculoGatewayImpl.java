package br.com.jlucaslopes.infrastructure.gateways.veiculo;

import br.com.jlucaslopes.application.gateways.VeiculoGateway;
import br.com.jlucaslopes.domain.entities.Cliente;
import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.application.exceptions.ClienteNaoEncontradoException;
import br.com.jlucaslopes.application.exceptions.VeiculoJaCadastradoException;
import br.com.jlucaslopes.application.exceptions.VeiculoNaoEncontradoException;
import br.com.jlucaslopes.domain.request.VeiculoCreateRequest;
import br.com.jlucaslopes.infrastructure.gateways.cliente.ClienteMapper;
import br.com.jlucaslopes.infrastructure.persistence.cliente.ClienteRepository;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoEntity;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoRepository;

import java.util.List;

public class VeiculoGatewayImpl implements VeiculoGateway {

    private final ClienteRepository clienteRepository;
    private final VeiculoRepository veiculoRepository;

    public VeiculoGatewayImpl(ClienteRepository clienteRepository, VeiculoRepository veiculoRepository) {
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Veiculo salvar(VeiculoCreateRequest veiculoRequest) {

        Cliente cliente = clienteRepository.findClienteByDocumento(veiculoRequest.getClienteDocumento())
                .map(ClienteMapper::toCliente)
                .orElseThrow(() -> new ClienteNaoEncontradoException("Cliente não encontrado"));

        veiculoRepository.findByPlaca(veiculoRequest.getPlaca())
                .ifPresent(veiculoExistente -> {
                    throw new VeiculoJaCadastradoException("Veículo com placa " + veiculoRequest.getPlaca() + " já cadastrado");
                });

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoRequest.getPlaca());
        veiculo.setFabricante(veiculoRequest.getFabricante());
        veiculo.setModelo(veiculoRequest.getModelo());
        veiculo.setAno(veiculoRequest.getAno());
        veiculo.setCliente(cliente);

        VeiculoEntity veiculoSaved = veiculoRepository.save(VeiculoMapper.toVeiculoEntity(veiculo));
        return VeiculoMapper.toVeiculo(veiculoSaved);
    }


    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id)
                .map(VeiculoMapper::toVeiculo)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado"));
    }

    public Veiculo atualizar(Long id, Veiculo veiculoAtualizado) {
        return veiculoRepository.findById(id)
                .map(veiculo -> {
                    veiculo.setPlaca(veiculoAtualizado.getPlaca());
                    veiculo.setFabricante(veiculoAtualizado.getFabricante());
                    veiculo.setModelo(veiculoAtualizado.getModelo());
                    veiculo.setAno(veiculoAtualizado.getAno());
                    return veiculoRepository.save(veiculo);
                })
                .map(VeiculoMapper::toVeiculo)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo não encontrado"));
    }

    public void deletar(Long id) {
        veiculoRepository.deleteById(id);
    }

    public List<Veiculo> findVeiculosByClienteDocumento(String documento) {
        return veiculoRepository.findByClienteDocumento(documento)
                .stream()
                .map(VeiculoMapper::toVeiculo)
                .toList();
    }

    public Veiculo findVeiculoByPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .map(VeiculoMapper::toVeiculo)
                .orElseThrow(() -> new VeiculoNaoEncontradoException("Veículo com placa " + placa + " não encontrado"));
    }
}
