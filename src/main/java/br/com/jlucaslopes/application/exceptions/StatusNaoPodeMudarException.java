package br.com.jlucaslopes.application.exceptions;

public class StatusNaoPodeMudarException extends RuntimeException {
    public StatusNaoPodeMudarException(String message) {
        super(message);
    }
}
