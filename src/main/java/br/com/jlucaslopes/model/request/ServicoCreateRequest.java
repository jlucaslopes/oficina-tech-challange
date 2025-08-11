package br.com.jlucaslopes.model.request;

import br.com.jlucaslopes.model.Peca;
import br.com.jlucaslopes.model.Servico;

public class ServicoCreateRequest {

    private String nome;
    private Long quantidade;
    private Double preco;
    private Long idPeca;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Long quantidade) {
        this.quantidade = quantidade;
    }

    public Long getIdPeca() {
        return idPeca;
    }

    public void setIdPeca(Long idPeca) {
        this.idPeca = idPeca;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }

    public Servico toServico(Peca peca) {
        Servico servico = new Servico();
        servico.setNome(this.nome);
        servico.setQuantidade(this.quantidade);
        servico.setPreco(peca.getValorUnitario() * this.quantidade);
        servico.setPeca(peca);
        return servico;
    }

    public Servico toServico() {
        Servico servico = new Servico();
        servico.setNome(this.nome);
        servico.setQuantidade(this.quantidade);
        servico.setPreco(this.preco);
        return servico;
    }
}
