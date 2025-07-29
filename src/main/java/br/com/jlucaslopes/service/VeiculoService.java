package br.com.jlucaslopes.service;

import br.com.jlucaslopes.model.Veiculo;
import br.com.jlucaslopes.repository.VeiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VeiculoService {

    private final VeiculoRepository veiculoRepository;

    @Autowired
    public VeiculoService(VeiculoRepository veiculoRepository) {
        this.veiculoRepository = veiculoRepository;
    }

    public Veiculo salvar(Veiculo veiculo) {
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
}
