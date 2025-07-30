package br.com.jlucaslopes.model.request;

public class VeiculoCreateRequest {

    private String placa;
    private String fabricante;
    private String modelo;
    private String ano;
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
