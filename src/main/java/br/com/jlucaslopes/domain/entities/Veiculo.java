package br.com.jlucaslopes.domain.entities;


import jakarta.validation.constraints.Pattern;

public class Veiculo {

    public static final String REGEX_VALIDA_PLACA = "^[A-Z]{3}\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$";

    private Long id;
    @Pattern(regexp = REGEX_VALIDA_PLACA,
            message ="Placa deve seguir o formato AAA0A00 ou AAA0A000")
    private String placa;
    private String fabricante;
    private String modelo;
    private String ano;
    private Cliente cliente;

    public Veiculo(Long id, String placa, String fabricante, String modelo, String ano, Cliente cliente) {
        this.id = id;
        this.placa = placa;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.ano = ano;
        this.cliente = cliente;
    }

    public Veiculo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getFabricante() {
        return fabricante;
    }

    public void setFabricante(String fabricante) {
        this.fabricante = fabricante;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
