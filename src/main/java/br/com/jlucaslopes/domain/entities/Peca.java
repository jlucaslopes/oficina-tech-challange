package br.com.jlucaslopes.domain.entities;

public class Peca {
    private Long id;
    private String descricao;
    private Double valorUnitario;
    private int quantidadeEstoque;

    public Peca(Long id, String descricao, Double valorUnitario, int quantidadeEstoque) {
        this.id = id;
        this.descricao = descricao;
        this.valorUnitario = valorUnitario;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public Peca() {
    }

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

    public Double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(Double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }
}
