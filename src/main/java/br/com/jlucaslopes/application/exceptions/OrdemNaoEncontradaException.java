package br.com.jlucaslopes.application.exceptions;

public class OrdemNaoEncontradaException extends RuntimeException {
    public OrdemNaoEncontradaException(String message) {
        super(message);
    }
}
