package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.Cliente;
import br.com.jlucaslopes.model.Veiculo;
import br.com.jlucaslopes.model.request.VeiculoCreateRequest;
import br.com.jlucaslopes.repository.ClienteRepository;
import br.com.jlucaslopes.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public VeiculoService(VeiculoRepository veiculoRepository, ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
        this.veiculoRepository = veiculoRepository;
    }

    public Veiculo salvar(VeiculoCreateRequest veiculoRequest) {

        Cliente cliente = clienteRepository.findClienteByDocumento(veiculoRequest.getClienteDocumento())
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado"));

        veiculoRepository.findByPlaca(veiculoRequest.getPlaca())
                .ifPresent((veiculoExistente) -> {
                    throw new RuntimeException("Veículo com placa " + veiculoRequest.getPlaca() + " já cadastrado");
                });

        Veiculo veiculo = new Veiculo();
        veiculo.setPlaca(veiculoRequest.getPlaca());
        veiculo.setFabricante(veiculoRequest.getFabricante());
        veiculo.setModelo(veiculoRequest.getModelo());
        veiculo.setAno(veiculoRequest.getAno());
        veiculo.setCliente(cliente);

        return veiculoRepository.save(veiculo);
    }


    public Veiculo buscarPorId(Long id) {
        return veiculoRepository.findById(id).orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
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
                .orElseThrow(() -> new RuntimeException("Veículo não encontrado"));
    }

    public void deletar(Long id) {
        veiculoRepository.deleteById(id);
    }

    public List<Veiculo> findVeiculosByClienteDocumento(String documento) {
        return veiculoRepository.findByClienteDocumento(documento);
    }

    public Veiculo findVeiculoByPlaca(String placa) {
        return veiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Veículo com placa " + placa + " não encontrado"));
    }
}
