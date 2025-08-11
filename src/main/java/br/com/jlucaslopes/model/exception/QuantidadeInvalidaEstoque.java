package br.com.jlucaslopes.model.exception;

public class QuantidadeInvalidaEstoque extends RuntimeException {
    public QuantidadeInvalidaEstoque(String message) {
        super(message);
    }
}
