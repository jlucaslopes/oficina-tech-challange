package br.com.jlucaslopes.infrastructure.gateways.ordemservico;

import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.infrastructure.gateways.servico.ServicoMapper;
import br.com.jlucaslopes.infrastructure.gateways.veiculo.VeiculoMapper;
import br.com.jlucaslopes.infrastructure.persistence.ordemservico.OrdemServicoEntity;

public class OrdemServicoMapper {

    public static OrdemServico toOrdemServico(OrdemServicoEntity entity) {
        return new OrdemServico(
                entity.getId(),
                entity.getDescricao(),
                entity.getDataInicio(),
                entity.getDataFim(),
                entity.getStatus(),
                VeiculoMapper.toVeiculo(entity.getVeiculo()),
                ServicoMapper.toServicoList(entity.getServicos())
        );
    }

    public static OrdemServicoEntity toEntity(OrdemServico ordemServico) {
        return new OrdemServicoEntity(
                ordemServico.getId(),
                ordemServico.getDescricao(),
                ordemServico.getDataInicio(),
                ordemServico.getDataFim(),
                ordemServico.getStatus(),
                VeiculoMapper.toVeiculoEntity(ordemServico.getVeiculo()),
                ServicoMapper.toEntityList(ordemServico.getServicos())
        );
    }
}
