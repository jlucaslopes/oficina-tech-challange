package br.com.jlucaslopes.model.exception;

public class CancelarOrdemException extends RuntimeException{

    public CancelarOrdemException(String message) {
        super(message);
    }

    public CancelarOrdemException(String message, Throwable cause) {
        super(message, cause);
    }
}
