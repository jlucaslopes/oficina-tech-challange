package br.com.jlucaslopes.infrastructure.gateways.servico;

import br.com.jlucaslopes.domain.entities.OrdemServico;
import br.com.jlucaslopes.domain.entities.Servico;
import br.com.jlucaslopes.infrastructure.gateways.ordemservico.OrdemServicoMapper;
import br.com.jlucaslopes.infrastructure.gateways.peca.PecaMapper;
import br.com.jlucaslopes.infrastructure.persistence.servico.ServicoEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class ServicoMapper {

    public static Servico toServico(ServicoEntity entity) {
        return new Servico(
                entity.getId(),
                entity.getNome(),
                entity.getQuantidade(),
                entity.getPreco(),
                OrdemServicoMapper.toOrdemServico(entity.getOrdemServico()),
                PecaMapper.toPeca(entity.getPeca())
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
                OrdemServicoMapper.toEntity(servico.getOrdemServico()),
                PecaMapper.toEntity(servico.getPeca())
        );
    }

    public static List<ServicoEntity> toEntityList(List<Servico> servicos) {
        return servicos.stream()
                .map(ServicoMapper::toEntity)
                .collect(Collectors.toList());
    }
}
