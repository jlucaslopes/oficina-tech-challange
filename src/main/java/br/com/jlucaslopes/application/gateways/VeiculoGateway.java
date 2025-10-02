package br.com.jlucaslopes.application.gateways;


import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.domain.request.VeiculoCreateRequest;

import java.util.List;

public interface VeiculoGateway {

    Veiculo salvar(VeiculoCreateRequest veiculoRequest) ;
    Veiculo buscarPorId(Long id);
    Veiculo atualizar(Long id, Veiculo veiculoAtualizado);
    void deletar(Long id);
    List<Veiculo> findVeiculosByClienteDocumento(String documento);
    Veiculo findVeiculoByPlaca(String placa);
}
