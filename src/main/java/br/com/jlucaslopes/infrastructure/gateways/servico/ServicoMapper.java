package br.com.jlucaslopes.infrastructure.gateways.servico;

import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.entities.Servico;
import br.com.jlucaslopes.infrastructure.gateways.ordemservico.OrdemServicoMapper;
import br.com.jlucaslopes.infrastructure.gateways.peca.PecaMapper;
import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoEntity;

import java.util.List;
import java.util.stream.Collectors;


public class ServicoMapper {

    public static Servico toServico(ServicoEntity entity) {
        return new Servico(
                entity.getId(),
                entity.getNome(),
                entity.getQuantidade(),
                entity.getPreco(),
                (entity.getOrdemServico() != null ? OrdemServicoMapper.toOrdemServico(entity.getOrdemServico()) : null),
                (entity.getPeca() != null ? PecaMapper.toPeca(entity.getPeca()) : null)
        );
    }

    public static List<Servico> toServicoList(List<ServicoEntity> servicosEntity) {
        return servicosEntity.stream()
                .map(ServicoMapper::toServico)
                .collect(Collectors.toList());
    }

    public static ServicoEntity toEntity(Servico servico) {
    return new ServicoEntity(
        servico.getId(),
        servico.getNome(),
        servico.getQuantidade(),
        servico.getPreco(),
        servico.getOrdemServico() != null ? OrdemServicoMapper.toEntity(servico.getOrdemServico()) : null,
        servico.getPeca() != null ? PecaMapper.toEntity(servico.getPeca()) : null
    );
    }

    public static List<ServicoEntity> toEntityList(List<Servico> servicos) {
    if (servicos == null) return List.of();
    return servicos.stream()
        .map(ServicoMapper::toEntity)
        .collect(Collectors.toList());
    }
}
