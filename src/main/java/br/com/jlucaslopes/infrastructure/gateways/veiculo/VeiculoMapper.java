package br.com.jlucaslopes.infrastructure.gateways.veiculo;

import br.com.jlucaslopes.domain.entities.Veiculo;
import br.com.jlucaslopes.infrastructure.gateways.cliente.ClienteMapper;
import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoEntity;

public class VeiculoMapper {

    public static Veiculo toVeiculo(VeiculoEntity entity) {
        if (entity == null) {
            return null;
        }
        return new Veiculo(
                entity.getId(),
                entity.getPlaca(),
                entity.getFabricante(),
                entity.getModelo(),
                entity.getAno(),
                ClienteMapper.toCliente(entity.getCliente())
        );
    }

    public static VeiculoEntity toVeiculoEntity(Veiculo veiculo) {
        if (veiculo == null) {
            return null;
        }
        VeiculoEntity entity = new VeiculoEntity();
        entity.setId(veiculo.getId());
        entity.setPlaca(veiculo.getPlaca());
        entity.setFabricante(veiculo.getFabricante());
        entity.setModelo(veiculo.getModelo());
        entity.setAno(veiculo.getAno());
        entity.setCliente(ClienteMapper.toEntity(veiculo.getCliente()));
        return entity;
    }

    public static VeiculoEntity toVeiculoEntity(VeiculoEntity veiculoEntity) {
        if (veiculoEntity == null) {
            return null;
        }
        VeiculoEntity entity = new VeiculoEntity();
        entity.setId(veiculoEntity.getId());
        entity.setPlaca(veiculoEntity.getPlaca());
        entity.setFabricante(veiculoEntity.getFabricante());
        entity.setModelo(veiculoEntity.getModelo());
        entity.setAno(veiculoEntity.getAno());
        entity.setCliente(veiculoEntity.getCliente());
        return entity;
    }
}
