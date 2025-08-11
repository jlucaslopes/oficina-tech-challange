package br.com.jlucaslopes.model.exception;

public class StatusNaoPodeMudarException extends RuntimeException {
    public StatusNaoPodeMudarException(String message) {
        super(message);
    }
}
