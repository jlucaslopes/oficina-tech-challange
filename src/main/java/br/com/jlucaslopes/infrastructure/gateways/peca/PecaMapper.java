package br.com.jlucaslopes.infrastructure.gateways.peca;

import br.com.jlucaslopes.domain.entities.Peca;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaEntity;

public class PecaMapper {

    public static PecaEntity toEntity(Peca peca) {
        return new PecaEntity(
                peca.getId(),
                peca.getDescricao(),
                peca.getValorUnitario(),
                peca.getQuantidadeEstoque()
        );
    }

    public static Peca toPeca(PecaEntity entity) {
        return new Peca(
                entity.getId(),
                entity.getDescricao(),
                entity.getValorUnitario(),
                entity.getQuantidadeEstoque()
        );
    }
}
