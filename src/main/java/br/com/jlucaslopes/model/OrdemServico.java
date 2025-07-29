package br.com.jlucaslopes.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class OrdemServico {

    @Id
    private Long id;
    private Status status;

    @ManyToOne
    @JoinColumn(name = "veiculo_id")
    private Veiculo veiculo;

    @OneToMany
    private List<Servico> servicos;

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }
}
