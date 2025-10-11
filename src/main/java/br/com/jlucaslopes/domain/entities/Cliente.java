package br.com.jlucaslopes.domain.entities;

import org.hibernate.validator.constraints.br.CPF;

public class Cliente {

    private Long id;
    @CPF
    private String documento;
    private String nome;


    public Cliente() {
    }

    public Cliente(String documento, String nome) {
        this.documento = documento;
        this.nome = nome;
    }

    public Cliente(Long id, String nome, String documento) {
        this.id = id;
        this.nome = nome;
        this.documento = documento;
    }

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
}
