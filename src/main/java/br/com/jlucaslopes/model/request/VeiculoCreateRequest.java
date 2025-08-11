package br.com.jlucaslopes.model.request;

import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.br.CPF;

public class VeiculoCreateRequest {

    public static final String REGEX_VALIDA_PLACA = "^[A-Z]{3}\\d{4}$|^[A-Z]{3}\\d[A-Z]\\d{2}$";
    @Pattern(regexp = REGEX_VALIDA_PLACA,
            message ="Placa deve seguir o formato AAA0A00 ou AAA0A000")
    private String placa;
    private String fabricante;
    private String modelo;
    private String ano;

    @CPF
    private String clienteDocumento;

    public VeiculoCreateRequest(String placa, String fabricante, String modelo, String ano, String clienteDocumento) {
        this.placa = placa;
        this.fabricante = fabricante;
        this.modelo = modelo;
        this.ano = ano;
        this.clienteDocumento = clienteDocumento;
    }

    public VeiculoCreateRequest() {
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

    public String getClienteDocumento() {
        return clienteDocumento;
    }

    public void setClienteDocumento(String clienteDocumento) {
        this.clienteDocumento = clienteDocumento;
    }
}
