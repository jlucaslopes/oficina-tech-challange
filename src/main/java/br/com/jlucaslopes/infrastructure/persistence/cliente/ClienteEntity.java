package br.com.jlucaslopes.infrastructure.persistence.cliente;

import br.com.jlucaslopes.infrastructure.persistence.veiculo.VeiculoEntity;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ClienteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String documento;
    private String nome;

    @OneToMany(mappedBy = "cliente" )
    @JsonManagedReference
    private List<VeiculoEntity> veiculos;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<VeiculoEntity> getVeiculos() {
        return veiculos;
    }

    public void setVeiculos(List<VeiculoEntity> veiculos) {
        this.veiculos = veiculos;
    }

    public ClienteEntity() {
    }

    public ClienteEntity(String nome, String documento) {
        this.nome = nome;
        this.documento = documento;
    }

    public ClienteEntity(Long id, String documento, String nome) {
        this.id = id;
        this.documento = documento;
        this.nome = nome;
    }
}
