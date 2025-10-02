package br.com.jlucaslopes.infrastructure.persistence.servico;

import br.com.jlucaslopes.infrastructure.persistence.ordemservico.OrdemServicoEntity;
import br.com.jlucaslopes.infrastructure.persistence.peca.PecaEntity;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class ServicoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private Long quantidade;
    private Double preco;

    @ManyToOne
    @JoinColumn(name = "ordem_id", insertable = true, updatable = true)
    @JsonBackReference
    private OrdemServicoEntity ordemServicoEntity;

    @ManyToOne
    @JoinColumn(name = "id_peca")
    private PecaEntity peca;

    public ServicoEntity(Long id, String nome, Long quantidade, Double preco, OrdemServicoEntity ordemServicoEntity, PecaEntity peca) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.preco = preco;
        this.ordemServicoEntity = ordemServicoEntity;
        this.peca = peca;
    }

    public ServicoEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PecaEntity getPeca() {
        return peca;
    }

    public void setPeca(PecaEntity peca) {
        this.peca = peca;
    }

    public OrdemServicoEntity getOrdemServico() {
        return ordemServicoEntity;
    }

    public void setOrdemServico(OrdemServicoEntity ordemServicoEntity) {
        this.ordemServicoEntity = ordemServicoEntity;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}
