package br.com.jlucaslopes.application.exceptions;

public class QuantidadeInvalidaEstoque extends RuntimeException {
    public QuantidadeInvalidaEstoque(String message) {
        super(message);
    }
}
