package br.com.jlucaslopes.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Peca {

    @Id
    private Long id;
    private String descricao;
    private Double valorUnitario;
    private int quantidadeEstoque;

    public Peca() {}

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidade) {
        this.quantidadeEstoque = quantidade;
    }

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valor) {
        this.valorUnitario = valor;
    }

}
